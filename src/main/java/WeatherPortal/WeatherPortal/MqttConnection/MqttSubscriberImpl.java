package WeatherPortal.WeatherPortal.MqttConnection;

import WeatherPortal.WeatherPortal.Config.MqttConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public class MqttSubscriberImpl extends MqttConfig implements MqttCallback {
    private String brokerUrl = null;
    final private String colon = ":";
    final private String clientId = UUID.randomUUID().toString();

    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;

    private static final Logger logger = LoggerFactory.getLogger(MqttSubscriberImpl.class);

    public MqttSubscriberImpl() {
        logger.info("Initializing Connection");
        this.config();
    }

    @Override
    protected void config(String _broker, Integer _port, Boolean _ssl, Boolean withAuth) {
        logger.info("Config connection with custom parameters");
        String protocol = this.TCP;
        this.brokerUrl = protocol + _broker + colon + _port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            this.connectionOptions.setPassword(this.password.toCharArray());
            this.connectionOptions.setUserName(this.username);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void config() {
        logger.info("Default Config");
        this.brokerUrl = this.TCP + this.broker + colon + this.port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(this.brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            this.connectionOptions.setPassword(this.password.toCharArray());
            this.connectionOptions.setUserName(this.password);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("Connection Lost " + throwable);
        this.config();
    }


    // Handle message arrival form MQTT side, not from cosumer side.
    @Override
    public void messageArrived(String mqttTopic, MqttMessage mqttMessage) throws Exception {

        // Here, we need to send the message to all devices after we apply the necessary modification
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("***********************************************************************");
        System.out.println("Message Arrived at Time: " + time + "  Topic: " + mqttTopic + "  Message: "
                + new String(mqttMessage.getPayload()));
        System.out.println("***********************************************************************");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    // Subscribe a new message to MQTT broker
    public void subscribeMessage(String topic) {
        try {
            this.mqttClient.subscribe(topic, this.qos);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
