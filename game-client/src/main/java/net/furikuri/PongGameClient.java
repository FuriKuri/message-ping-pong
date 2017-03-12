package net.furikuri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Profile("pong")
public class PongGameClient {

    @Autowired
    private KafkaTemplate<String, String> template;

    private Random random = new Random();

    @KafkaListener(id = "pong", topics = "pong")
    public void pong(String messageContent) {
        System.out.println("pong");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (random.nextInt(100) < 80) {
            template.send("ping", messageContent + ":pong");
        } else {
            template.send("controller", messageContent + ":pong");
        }
        template.flush();
    }

}
