package com.shared.printer.management.MQTT;


import com.alibaba.fastjson.JSONObject;
import com.shared.printer.management.entity.TemHum;
import com.shared.printer.management.entity.goods;
import com.shared.printer.management.mapper.printerMapper;
import io.swagger.models.auth.In;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.AbstractMqttMessageHandler;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageHandler;

import java.time.LocalDateTime;

/***
 * 配置类 只有mqtt.services配置时才会加载此类
 */
@Configuration
@ConditionalOnProperty("mqtt.services")
public class MQTTConfig implements ApplicationListener<ApplicationEvent> {

    /**
     * goodsMapper
     */
    @Autowired
    private printerMapper printerMapperq;


    /**
     * 发送数据使用的接口
     */
    @Autowired
    MsgGateway msgGateway;
    //private final MsgSendService msgSendService;//发布消息到消息中间件接口

    /**
     * 接收数据客户端ID
     */
    @Value("${mqtt.appid:mqtt_id}")
    private String appid;

    /**
     * 发送数据时客户端ID 不能与接收数据的appid相同
     * 在集成的时候入站与出站消息处理并不使用同一个连接，所以如果clien ID相同，将会出现Mqtt反复重连现象，实为 mqtt 出入站连接交替踢对方下线
     */
    @Value("${mqtt.outAppId:mqtt_out_id}")
    private String outAppId;

    /**
     * 订阅主题，可以是多个主题
     */
    @Value("${mqtt.input.topic:mqtt_input_topic}")
    private String[] inputTopic;

    /**
     * 发布主题，可以是多个主题
     */
    @Value("${mqtt.out.topic:mqtt_out_topic}")
    private String[] outTopic;

    /**
     * 服务器地址以及端口
     */
    @Value("${mqtt.services:#{null}}")
    private String[] mqttServices;

    /**
     * emqx服务器用户名
     */
    @Value("${mqtt.user:#{null}}")
    private String user;

    /**
     * emqx服务器密码
     */
    @Value("${mqtt.password:#{null}}")
    private String password;

    /**
     * 心跳时间,默认为5分钟
     */
    @Value("${mqtt.KeepAliveInterval:300}")
    private Integer KeepAliveInterval;

    /**
     * 是否不保持session,默认为session保持
     */
    @Value("${mqtt.CleanSession:false}")
    private Boolean CleanSession;

    /**
     * //是否自动重联，默认为开启自动重联
     */
    @Value("${mqtt.AutomaticReconnect:true}")
    private Boolean AutomaticReconnect;

    /**
     * 连接超时,默认为30秒
     */
    @Value("${mqtt.CompletionTimeout:30000}")
    private Long CompletionTimeout;


    /**
     *  通信质量，详见MQTT协议
     */
    @Value("${mqtt.Qos:1}")
    private Integer Qos;


//    public MQTTConfig(MsgSendService msgSendService) {
//        this.msgSendService = msgSendService;
//    }

    /**
     * MQTT连接配置
     * @return 连接工厂
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        //连接工厂类
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        //连接参数
        MqttConnectOptions options = new MqttConnectOptions();
        //连接地址
        options.setServerURIs(mqttServices);
        if(null!=user) {
            //用户名
            options.setUserName(user);
        }
        if(null!=password) {
            //密码
            options.setPassword(password.toCharArray());
        }
        //心跳时间
        options.setKeepAliveInterval(KeepAliveInterval);
        //断开是否自动重联
        options.setAutomaticReconnect(AutomaticReconnect);
        //保持session
        options.setCleanSession(CleanSession);
        factory.setConnectionOptions(options);
        return factory;
    }

    /** 4
     * 注入这个adapter 在mqttservice中使用 动态增加和删除订阅的topic
     * @param factory
     * @return
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter adapter(MqttPahoClientFactory factory){
        return new MqttPahoMessageDrivenChannelAdapter(appid,factory,inputTopic);
    }
    /**
     * 入站管道 管理消息接收的 采用配置文件中配置的topic
     * @param  mqttPahoClientFactory
     * @return MessageProducerSupport
     */
    @Bean
    public MessageProducerSupport mqttInput(MqttPahoClientFactory mqttPahoClientFactory){
        //建立订阅连接
//        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(appid, mqttPahoClientFactory, inputTopic);
        MqttPahoMessageDrivenChannelAdapter adapter = adapter(mqttPahoClientFactory);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        //bytes类型接收
        converter.setPayloadAsBytes(true);
        //连接超时的时间
        adapter.setCompletionTimeout(CompletionTimeout);
        adapter.setConverter(converter);
        //消息质量
        adapter.setQos(Qos);
        //输入管道名称 自定义
        adapter.setOutputChannelName(ChannelName.INPUT_DATA);
        return adapter;
    }
    /**
     * 向服务器发送数据管道绑定  管理发送的 指定发送时的客户端id
     * @param connectionFactory tcp连接工厂类
     * @return 消息管道对象
     */
    @Bean
    @ServiceActivator(inputChannel = ChannelName.OUTPUT_DATA_MQTT)
    public AbstractMqttMessageHandler MQTTOutAdapter(MqttPahoClientFactory connectionFactory) {
        //创建一个新的出站管道，由于MQTT的发布与订阅是两个独立的连接，因此客户端的ID(即APPID）不能与订阅时所使用的ID一样，否则在服务端会认为是同一个客户端，而造成连接失败
        MqttPahoMessageHandler outGate = new MqttPahoMessageHandler(outAppId, connectionFactory);
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        //bytes类型接收
        converter.setPayloadAsBytes(true);
        outGate.setAsync(true);
        //设置连接超时时时
        outGate.setCompletionTimeout(CompletionTimeout);
        //设置通信质量
        outGate.setDefaultQos(Qos);
        outGate.setConverter(converter);
        return outGate;
    }

    /**
     * MQTT连接时调用的方法
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MqttSubscribedEvent) {
            String msg = "OK";
            /**------------------连接时需要发送起始消息，写在这里-------------**/
            //MsgGateway.send(MessageBuilder.withPayload(msg.getBytes()).build());
//            msgGateway.sendWithTopic(outTopic[0],"hello");
        }
    }

    /**
     * 消息接收回调 收到订阅的消息会到此处理
     * @return
     */
    @Bean
    //使用ServiceActivator 指定接收消息的管道为 mqttInboundChannel，投递到mqttInboundChannel管道中的消息会被该方法接收并执行
    @ServiceActivator(inputChannel = ChannelName.INPUT_DATA)
    public MessageHandler handleMessage() {
        return message -> {
            //将字节数组转为字符串
            String msg = new String((byte[]) message.getPayload());
            System.out.println("接收到的消息为：" + msg);
            try {
                //将字符串转为json对象
                JSONObject jsonObject = JSONObject.parseObject(msg);
                //获取消息类型 printerMapperq
                String type = jsonObject.getString("type");
                if (type.equals("1")){
                    //获取打印机的ID
                    String id = jsonObject.getString("id");
                    //获取打印机的状态
                    String status = jsonObject.getString("status");
                    if (status.equals("offline")){
                        status = "离线";
                    }else if (status.equals("online")){
                        status = "在线";
                    }
                    System.out.println("更新打印机状态：ID："+id+" 状态："+status);
                    //更新打印机状
                    printerMapperq.updateState(Integer.valueOf(id),status);
                }
            } catch (Exception e) {
                System.out.println("消息格式错误");
                System.out.println("接收到的消息为：" + msg);
            }
        };
    }
}
