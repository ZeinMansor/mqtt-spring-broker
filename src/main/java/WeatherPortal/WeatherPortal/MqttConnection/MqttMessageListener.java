package WeatherPortal.WeatherPortal.MqttConnection;

import org.springframework.beans.factory.annotation.Autowired;

public class MqttMessageListener implements Runnable{
    @Autowired
    MqttSubscriberImpl subscriber;
    public void run() {
        while (true) {
            subscriber.subscribeMessage("weather-data-test");
        }
    }
}
