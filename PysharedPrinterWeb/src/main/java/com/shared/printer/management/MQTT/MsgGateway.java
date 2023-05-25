package com.shared.printer.management.MQTT;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@MessagingGateway
@Component
public interface MsgGateway {

    /**
     * MQTT 发送网关
     * @param a 主题，可以指定不同的数据发布主题，在消息中间件里面体现为不同的消息队列
     * @param out 消息内容
     */
    @Gateway(requestChannel = ChannelName.OUTPUT_DATA_MQTT)
    void send(@Header(MqttHeaders.TOPIC) String a, Message<byte[]> out);

    @Gateway(requestChannel = ChannelName.OUTPUT_DATA_MQTT)
    void sendToMqtt(String text);
    //指定topic发送
    @Gateway(requestChannel = ChannelName.OUTPUT_DATA_MQTT)
    void sendWithTopic(@Header(MqttHeaders.TOPIC) String topic, String text);
    void sendWithTopicAndQos(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer Qos, String text);
}
