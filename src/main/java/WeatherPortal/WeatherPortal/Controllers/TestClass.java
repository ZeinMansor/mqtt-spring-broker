package WeatherPortal.WeatherPortal.Controllers;


import WeatherPortal.WeatherPortal.MqttConnection.MqttPublisherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestClass {

    @Autowired
    MqttPublisherImpl publisher;

    @GetMapping
    public String test() {
        publisher.publishMessage("weather-data-test", "Hi");

        return "";
    }

}
