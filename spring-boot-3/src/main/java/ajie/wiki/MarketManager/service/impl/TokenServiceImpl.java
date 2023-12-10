package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.service.TokenService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${global.redis.host:localhost}")
    private String redisHost;
    @Value("${global.redis.port:6379}")
    private int redisPort;

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Override
    public boolean Authorization(String userId, String token) {
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)) {
            try (Jedis jedis = new Jedis(redisHost, redisPort)) {
                String storedToken = jedis.get(userId);
                return token.equals(storedToken);
            }
        }else{
            logger.info("Id: "+userId+" 会话超时");
            return false;
        }
    }

    @Override
    public String Generate(Integer userId) {
        String uid = String.valueOf(userId);
        String token = UUID.randomUUID().toString();
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            if (jedis.get(uid) != null) {
                jedis.del(uid);
            }
            jedis.set(userId.toString(), token, new SetParams().px(3600000L));
        }
        return token;
    }

    @Override
    public boolean remove(String userId) {
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            if (jedis.get(userId) != null) {
                jedis.del(userId);
                return true;
            } else {
                logger.warn(userId + "  不存在的用户刪除键值对");
                return false;
            }
        }
    }
}
