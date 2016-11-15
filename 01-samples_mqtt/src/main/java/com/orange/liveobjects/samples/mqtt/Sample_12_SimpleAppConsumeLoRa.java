/*
 * Copyright (C) 2016 Orange
 *
 * This software is distributed under the terms and conditions of the 'BSD-3-Clause'
 * license which can be found in the file 'LICENSE.txt' in this package distribution
 * or at 'https://opensource.org/licenses/BSD-3-Clause'.
 */
package com.orange.liveobjects.samples.mqtt;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orange.liveobjects.samples.utils.LoraData;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

/**
 * Application connects to LO and consumes LoRa messages.
 */
public class Sample_12_SimpleAppConsumeLoRa {

    /**
     * Basic "MqttCallback" that handles messages as JSON device commands,
     * and immediately respond.
     */
    public static class SimpleMqttCallback implements MqttCallback {

        private MqttClient mqttClient;
        private Gson gson = new Gson();

        public SimpleMqttCallback(MqttClient mqttClient) {
            this.mqttClient = mqttClient;
        }

        public void connectionLost(Throwable throwable) {
            System.out.println("Connection lost");
            mqttClient.notifyAll();
        }

        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            System.out.println("Received message - " + mqttMessage);

            // Decoding the LoRa message
            JsonObject mqttPayload = gson.fromJson(new String(mqttMessage.getPayload()), JsonObject.class);
            String liveObjectsPayload = mqttPayload.get("payload").getAsString();
            LoraData loraData = gson.fromJson(liveObjectsPayload, LoraData.class);
            System.out.println("Decoded LoRa message - " + loraData);
        }

        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            // nothing
        }
    }

    public static void main(String[] args) throws InterruptedException {

        String API_KEY = "<<< REPLACE WITH valid API key value>>>"; // <-- REPLACE!

        String SERVER = "tcp://liveobjects.orange-business.com:1883";
        String APP_ID = "app:" + UUID.randomUUID().toString();

        MqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(SERVER, APP_ID, new MemoryPersistence());

            // register callback (to handle received commands
            mqttClient.setCallback(new SimpleMqttCallback(mqttClient));

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName("json+bridge"); // selecting mode "Bridge"
            connOpts.setPassword(API_KEY.toCharArray()); // passing API key value as password
            connOpts.setCleanSession(true);

            // Connection
            System.out.printf("Connecting to broker: %s ...%n", SERVER);
            mqttClient.connect(connOpts);
            System.out.println("... connected.");

            // For all LoRa devices
            final String ROUTING_KEY_FILTER = "router/~event/v1/data/new/urn/lora/#";
            // For a specific device
            // final String ROUTING_KEY_FILTER = "router/~event/v1/data/new/urn/lora/0123456789ABCDEF/#";

            // Subscribe to commands
            System.out.printf("Consuming from Router with filter '%s'...%n", ROUTING_KEY_FILTER);
            mqttClient.subscribe(ROUTING_KEY_FILTER);
            System.out.println("... subscribed.");

            synchronized (mqttClient) {
                mqttClient.wait();
            }

        } catch (MqttException me) {
            me.printStackTrace();

        } finally {
            // close client
            if (mqttClient != null && mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
