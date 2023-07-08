package WeatherPortal.WeatherPortal;

import WeatherPortal.WeatherPortal.MqttConnection.MqttSubscriberImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class MqttMessageListener implements Runnable{
    @Autowired
    MqttSubscriberImpl subscriber;
    @Override
    public void run() {
        System.out.println("INFO: Started Running the task");
        while (true) {
            subscriber.subscribeMessage("weather-data-test");
        }
    }
}
