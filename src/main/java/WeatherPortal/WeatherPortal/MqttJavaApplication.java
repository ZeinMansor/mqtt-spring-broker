package WeatherPortal.WeatherPortal;

import WeatherPortal.WeatherPortal.MqttConnection.MqttSubscriberImpl;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;

import jakarta.websocket.MessageHandler;

@SpringBootApplication
public class MqttJavaApplication extends SpringBootServletInitializer {

    @Autowired
    Runnable MessageListener;

    @Autowired
    MqttSubscriberImpl subscriber;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MqttJavaApplication.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(MqttJavaApplication.class, args);
    }

    @Bean
    public CommandLineRunner schedulingRunner(TaskExecutor taskExecutor) {


        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                while (true) {
                    subscriber.subscribeMessage("weather-data-test");
                }
                // taskExecutor.execute(MessageListener);
            }
        };
    }


}