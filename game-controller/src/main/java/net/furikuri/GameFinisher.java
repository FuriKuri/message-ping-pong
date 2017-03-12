package net.furikuri;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GameFinisher {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    @KafkaListener(id = "controller", topics = "controller")
    public void finish(String messageContent) {
        System.out.println("Finish");
        String[] parts = messageContent.split(":");
        String id = parts[0];
        String winner = parts[parts.length - 1];

        valueOps.set(id, winner);
    }
}
