package org.wso2.iot.agent.events.publisher;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.wso2.iot.agent.api.DeviceInfo;
import org.wso2.iot.agent.events.beans.EventPayload;
import org.wso2.iot.agent.proxy.IdentityProxy;


/**
 * Created by hhiroot on 4/21/17.
 */

public class MqttDataPublisher implements DataPublisher {

    private static final String TAG = HttpDataPublisher.class.getName();
    private DeviceInfo deviceInfo;
    public MqttDataPublisher(Context context){
        deviceInfo = new DeviceInfo(context);
    }

    @Override
    public void publish(EventPayload eventPayload) {
        String topic = deviceInfo.getTenantDomain()+"/"+deviceInfo.getTenantDomainGroup()+"/"+deviceInfo.getDeviceId()+"/"+deviceInfo.getTenantDomainDataInstance();
        String content;
        try { content = eventPayload.getPayload(); }
        catch (NullPointerException e) { content = ""; }
        int qos = 0;
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient obdMqttClient = new MqttClient(deviceInfo.getBrokerProtocol()+deviceInfo.getBrokerIP()+":"+deviceInfo.getBrokerPort(), deviceInfo.getDeviceId(), persistence);
            if (!obdMqttClient.isConnected()){
                obdMqttClient = createConnect(obdMqttClient);
            }
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            obdMqttClient.publish(topic, message);
        } catch(MqttException me) {
            Log.e(TAG, "Cannot make connection, Check the server IP address i.e: tcp://localhost:1886");
        }
        catch (NullPointerException ne){
            Log.e (TAG, "Message cannot readable, possibly 'null' data is coming from OBD device ");
        }
    }

    private MqttClient createConnect(MqttClient client){
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(IdentityProxy.getValidCurrentToken());
        connOpts.setPassword("".toCharArray());
        connOpts.setCleanSession(true);
        try {
            client.connect(connOpts);
        } catch (MqttException e) {
            Log.e(TAG, "Cannot make connection, Check the server IP address i.e: tcp://localhost:1886");
            return null;
        }
        return client;
    }


}
