package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.mapper.LevelMapper;
import ajie.wiki.MarketManager.mapper.UserMapper;
import ajie.wiki.MarketManager.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final LevelMapper levelMapper;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    public PermissionServiceImpl(LevelMapper levelMapper, UserMapper userMapper) {
        this.levelMapper = levelMapper;
        this.userMapper = userMapper;
    }

    @Override
    public boolean hasManagerPermission(Integer userId) {
        User judgeUser=userMapper.findById(userId);
        Integer managerLevel = levelMapper.getManagerLevel();
        boolean result=judgeUser.getLevel() >= managerLevel;
        if(!result){
            logger.warn(userId + "  没有权限，需要经理权限");
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean hasOwnerPermission(Integer userId) {
        User judgeUser=userMapper.findById(userId);
        Integer ownerLevel = levelMapper.getOwnerLevel();
        boolean result=judgeUser.getLevel() >= ownerLevel;
        if(!result){
            logger.warn(userId + "  没有权限，需要经理权限");
            return false;
        }else{
            return true;
        }
    }
}
