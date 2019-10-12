package com.shuojie.mqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class SubMsg {
 
//	 private static String topic = "$share/testgroup/wyptest1";
//	 private static String topic = "$queue/wyptest1";
//	 private static String topic = "wyptest1";
     private static int qos = 2;
     private static String broker = "tcp://47.98.193.195:1883";
     private static String userName = "suojie";
     private static String passWord = "123456";
    
 
     private static MqttClient connect(String clientId) throws MqttException{
    	 MemoryPersistence persistence = new MemoryPersistence();
    	 MqttConnectOptions connOpts = new MqttConnectOptions();
//    	 String[] uris = {"tcp://10.100.124.206:1883","tcp://10.100.124.206:1883"};
    	 connOpts.setCleanSession(false);
         connOpts.setUserName(userName);
         connOpts.setPassword(passWord.toCharArray());
         connOpts.setConnectionTimeout(10);
         connOpts.setKeepAliveInterval(20);
//         connOpts.setServerURIs(uris);
//         connOpts.setWill(topic, "close".getBytes(), 2, true);
         MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
         mqttClient.connect(connOpts);
    	 return mqttClient;
     }
     
     public static void sub(MqttClient mqttClient,String topic) throws MqttException{
         int[] Qos  = {qos};
         String[] topics = {topic};
         mqttClient.subscribe(topics, Qos);
     }
     
     
    private static void runsub(String clientId, String topic) throws MqttException{
    	MqttClient mqttClient = connect(clientId);
    	if(mqttClient != null){
			sub(mqttClient,topic);
    	}
    }
    public static void main(String[] args) throws MqttException{

		runsub("client-id-1", "demo/test");//"$share/testgroupa/edge/server/private/+"
    }
}