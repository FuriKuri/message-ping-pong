package net.furikuri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Profile("ping")
public class PingGameClient {

    @Autowired
    private KafkaTemplate<String, String> template;

    private Random random = new Random();

    @KafkaListener(id = "ping", topics = "ping")
    public void ping(String messageContent) {
        System.out.println("ping");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextInt(100) < 80) {
            template.send("pong", messageContent + ":ping");
        } else {
            template.send("controller", messageContent + ":ping");
        }
        template.flush();
    }

}
