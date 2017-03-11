package net.furikuri;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class GameRestController {

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @RequestMapping("/")
    public String create() {
        setOps.add(UUID.randomUUID().toString(), "running");
        return "ok";
    }

}
