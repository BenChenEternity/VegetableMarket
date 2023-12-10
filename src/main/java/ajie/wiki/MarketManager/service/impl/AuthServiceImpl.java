package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.Tool.SHA256;
import ajie.wiki.MarketManager.entity.post.UserCredentials;
import ajie.wiki.MarketManager.entity.post.UserRegForm;
import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.mapper.LevelMapper;
import ajie.wiki.MarketManager.mapper.UserMapper;
import ajie.wiki.MarketManager.service.AuthService;
import ajie.wiki.MarketManager.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;

    private final TokenService tokenService;
    private final LevelMapper levelMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    public AuthServiceImpl(UserMapper userMapper, TokenService tokenService, LevelMapper levelMapper) {
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.levelMapper = levelMapper;
    }

    @Override
    public HashMap<String, String> login(UserCredentials userCredentials) {
        HashMap<String, String> result = new HashMap<>();
        User user = userMapper.findByNickname(userCredentials.getUsername());
        if (user != null) {
            if (Objects.requireNonNull(SHA256.hash(userCredentials.getPassword())).equalsIgnoreCase(user.getPassword())) {
                result.put("token", tokenService.Generate(user.getId()));
                result.put("id", String.valueOf(user.getId()));
                result.put("level", String.valueOf(user.getLevel()));
                result.put("avatar", user.getAvatar());
                if (Objects.equals(user.getLevel(), levelMapper.getUserLevel())) {
                    logger.info(user.getId() + "  普通用户登录");
                    result.put("code", "200");//用户登录
                } else if (Objects.equals(user.getLevel(), levelMapper.getVIPLevel())) {
                    logger.info(user.getId() + "  VIP用户登录");
                    result.put("code", "201");//VIP登录
                } else if (Objects.equals(user.getLevel(), levelMapper.getManagerLevel())) {
                    logger.info(user.getId() + "  经理登录");
                    result.put("code", "202");//经理登录
                } else if (Objects.equals(user.getLevel(), levelMapper.getOwnerLevel())) {
                    logger.info(user.getId() + "  所有者登录");
                    result.put("code", "203");//所有者登录
                }
            } else {
                result.put("code", "300");//密码错误
            }
        } else {
            result.put("code", "400");//没有此用户
        }
        return result;
    }

    @Override
    public HashMap<String, String> register(UserRegForm userRegForm) {
        User existingUser = userMapper.findByNickname(userRegForm.getNickname());
        HashMap<String, String> result = new HashMap<>();
        if (existingUser != null) {
            result.put("code", "101");//用户名已经存在
            return result;
        }
        User userInsert = new User();
        userInsert.setPassword(userRegForm.getPassword());
        userInsert.setNickname(userRegForm.getNickname());
        userInsert.setGender(userRegForm.getGender());
        userInsert.setLevel(1);
        int linesInsert = userMapper.insertUser(userInsert);
        if (linesInsert == 1) {
            logger.info(userRegForm.getNickname() + " 注册成功");
            result.put("code", "100");//注册成功
        } else if (linesInsert == 0) {
            logger.info(userRegForm.getNickname() + " 注册失败");
            result.put("code", "102");//注册失败
        } else {
            logger.info(userRegForm.getNickname() + " 注册出现异常，可能插入了 " + linesInsert + " 行");
            result.put("code", "103");//注册出现异常
        }
        return result;
    }

    @Override
    public boolean logout(String userId, String sessionToken) {
        if (!tokenService.Authorization(userId, sessionToken)) {
            return false;
        } else {
            logger.info(userId + "  登出");
            return tokenService.remove(userId);
        }
    }
}
