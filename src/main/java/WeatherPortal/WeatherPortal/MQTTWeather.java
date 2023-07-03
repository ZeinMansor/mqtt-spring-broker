package WeatherPortal.WeatherPortal;

import java.io.FileInputStream;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;



public class MQTTWeather {
	private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String MQTT_TOPIC = "wetterdaten";
    
    public void getWeatherData(){
    	try {
            // MQTT-Verbindung herstellen
            MqttClient client = new MqttClient(BROKER_URL, MqttClient.generateClientId());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect(options);

            // API-Schlüssel aus einer Konfigurationsdatei laden
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("application.properties");
            properties.load(fileInputStream);
            String apiKey = properties.getProperty("openweathermap.api.key");

            // Wetterdaten von OpenWeatherMap abrufen
            //String weatherData = getWeatherData(apiKey);

            String weatherData = "test";
			// Wetterdaten auf MQTT-Broker veröffentlichen
            client.publish(MQTT_TOPIC, weatherData.getBytes(), 0, false);

            // MQTT-Verbindung trennen
            client.disconnect();
            System.out.println("Verbindung getrennt");
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
