package WeatherPortal.WeatherPortal.MqttConnection;

import WeatherPortal.WeatherPortal.Config.MqttConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MqttPublisherImpl extends MqttConfig implements MqttCallback {

    private String brokerUrl = null;
    final private String colon = ":";
    final private String clientId = UUID.randomUUID().toString();

    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;
    private static final Logger logger = LoggerFactory.getLogger(MqttPublisherImpl.class);

    private MqttPublisherImpl() {
        this.config();
    }

    private MqttPublisherImpl(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
        this.config(broker, port, ssl, withUserNamePass);
    }

    public static MqttPublisherImpl getInstance() {
        return new MqttPublisherImpl();
    }

    public static MqttPublisherImpl getInstance(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
        return new MqttPublisherImpl(broker, port, ssl, withUserNamePass);
    }

    @Override
    protected void config(String _broker, Integer _port, Boolean _ssl, Boolean withAuth) {
        // Similar to MqttSubscriberIpl, not used in our application
    }

    @Override
    protected void config() {
        logger.info("Default Config");
        this.brokerUrl = this.TCP + this.broker + colon + this.port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        System.out.println("INFO: broker url " + this.brokerUrl);
        try {
            this.mqttClient = new MqttClient(this.brokerUrl, clientId, persistence);
            this.connectionOptions.setCleanSession(true);
            this.connectionOptions.setPassword(this.password.toCharArray());
            this.connectionOptions.setUserName(this.username);
            this.mqttClient.connect(this.connectionOptions);
            this.mqttClient.setCallback(this);
        } catch (MqttException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("Connection Lost");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        // Blank since we only send messages to the subscriber
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("delivery completed");
    }

    public void publishMessage(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(this.qos);
            mqttMessage.setRetained(false);
            this.mqttClient.publish(topic, mqttMessage);
        } catch (MqttException me) {
            logger.error("Error " + me);
        }
    }

    public void disconnect() {
        try {
            this.mqttClient.disconnect();
        } catch (MqttException me) {
            logger.error("ERROR", me);
        }
    }
}
