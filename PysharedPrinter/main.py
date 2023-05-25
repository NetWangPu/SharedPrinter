#共享打印机的设备端
# 1. 从服务器端获取打印任务 使用的是MQTT协议 收到打印的指令
# 2. 打印任务
# 3. 打印完成后，将打印结果发送给服务器端
# 4. 服务器端接收到打印结果后，将打印结果发送给服务器
# 5. 删除文件

import paho.mqtt.client as mqtt
import os
import json
import random
import string

#{"printerid":"1","id":"1","Printnumber":"1","Printcolor":"1","Printfile":"1PrintcontentPrinttimePrintstatusPrintresult"}
#打印机ID 订单ID 份数 颜色1是黑白2是彩色 打印文件下载链接

# 订阅主题
TOPIC = "sharedprintDevice1"
# 服务器地址
HOST = "120.27.237.245"
# 端口号
PORT = 1883
# 设备ID
CLIENT_ID = "1"
#定义LInux下的文件缓存路径
FILE_PATH = "/home/sharedprint/file/"


#解析函数 解析{"id":"1","Printnumber":"1","Printcolor":"1","Printfile":"1PrintcontentPrinttimePrintstatusPrintresult"}
def parse_message(message):
    #解析json
    json_data = json.loads(message)
    #打印机ID
    id = json_data["printerid"]
    #订单ID
    order_id = json_data["id"]
    #打印份数
    print_number = json_data["Printnumber"]
    #打印颜色
    print_color = json_data["Printcolor"]
    #打印文件
    print_file = json_data["Printfile"]
    return id, order_id,print_number, print_color, print_file

#订阅MQTT
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe(TOPIC)
    print("subscribe topic: " + TOPIC)

#接收消息
def on_message(client, userdata, msg):
    print("Received a message on topic: " + msg.topic)
    print("Received a message: " + str(msg.payload.decode("utf-8")))
    #打印文件
    print_file(str(msg.payload.decode("utf-8")))


#主函数
def main():
    # 创建MQTT客户端
    client = mqtt.Client(CLIENT_ID)
    # 连接MQTT服务器
    client.connect(HOST, PORT, 60)
    # 设置消息回调
    client.on_connect = on_connect
    client.on_message = on_message
    # 循环接收消息
    client.loop_forever()
    #设置遗嘱
    client.will_set("sharedprintDevice", "{\"type\":\"1\",\"id\":\"1\",\"status\":\"offline\"}", 2, False)
    #上线发送消息
    client.publish("sharedprintDevice", "{\"type\":\"1\",\"id\":\"1\",\"status\":\"online\"}", 2, False)
    #{"type":"1","id":"1","status":"online"}

#打印机打印文件
def print_file(print_msg):
    #解析json
    id, order_id, print_number, print_color, print_file = parse_message(print_msg)
    #判断是否本机id
    if id != CLIENT_ID:
        return False
    #下载文件 curl -X GET "http://39.98.181.193:8091/download?fileName=1684975934491.pdf" -H "accept: */*" 下载文件 保存
    file_name = ''.join(random.sample(string.ascii_letters + string.digits, 8)) + ".pdf"
    #下载文件
    os.system("curl -X GET \"http://39.98.181.193:8091/download?fileName=" + print_file + "\" -H \"accept: */*\" > " + FILE_PATH + file_name)
    #打印并删除文件
    os.system("lpr -# " + print_number + " -r " + FILE_PATH + file_name)
    #延迟5秒 保证文件传输打印机完成
    # time.sleep(5)
    #删除服务器文件 curl -X GET "http://39.98.181.193:8091/deletefile?fileName=1684975934491.pdf" -H "accept: */*" 删除服务器文件
    # os.system("curl -X GET \"http://39.98.181.193:8091/deletefile?fileName=" + print_file + "\" -H \"accept: */*\"")
    return True

if __name__ == '__main__':
    main()