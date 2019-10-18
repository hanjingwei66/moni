package com.shuojie.mqttClient;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 发送数据到mqtt服务器
 * @author：涂有
 * @date 2017年8月16日 下午11:15:22
 */
public class PubMsg {
	private static int qos = 2; //只有一次
	private static String broker = "tcp://47.98.193.195:1883";
	private static String userName = "suojie";
	private static String passWord = "123456";
 
	
	private static MqttClient connect(String clientId,String userName,
			String password) throws MqttException {
		MemoryPersistence persistence = new MemoryPersistence();
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		connOpts.setUserName(userName);
		connOpts.setPassword(password.toCharArray());
		connOpts.setConnectionTimeout(10);
		connOpts.setKeepAliveInterval(20);
//		String[] uris = {"tcp://10.100.124.206:1883","tcp://10.100.124.207:1883"};
//		connOpts.setServerURIs(uris);  //起到负载均衡和高可用的作用
		MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
		mqttClient.setCallback(new PushCallback("test"));
		mqttClient.connect(connOpts);
		return mqttClient;
	}
 
	private static void pub(MqttClient sampleClient, byte[] msg,String topic)
			throws MqttPersistenceException, MqttException {

		MqttMessage message = new MqttMessage(msg);
		message.setQos(qos);
		message.setRetained(false);
		sampleClient.publish(topic, message);
	}
	
	public static void publish(byte[] msg,String clientId,String topic) throws MqttException{
		MqttClient mqttClient = connect(clientId,userName,passWord);
 
		if (mqttClient != null) {
			pub(mqttClient, msg, topic);
			System.out.println("pub-->" + msg);
		}
 
		if (mqttClient != null) {
			mqttClient.disconnect();
		}
	}
 
//	public static void main(String[] args) throws MqttException {
//		publish("message content","client-id-0","$share/edge/server/public/a");
//	}
}
 
