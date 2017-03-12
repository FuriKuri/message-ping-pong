package net.furikuri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class GameRestController {

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    @Autowired
    private KafkaTemplate<String, String> template;

    @RequestMapping("/create")
    public String create() {
        String gameId = UUID.randomUUID().toString();
        valueOps.set(gameId, "running");
        template.send("ping", gameId);
        template.flush();
        return gameId;
    }

    @RequestMapping("/{id}")
    public String get(@PathVariable("id") String id) {
        return valueOps.get(id);
    }

}
