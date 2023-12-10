package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.entity.response.UserInfoResponse;
import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.mapper.UserMapper;
import ajie.wiki.MarketManager.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public UserInfoResponse findInfoById(Integer id) {
        return new UserInfoResponse(userMapper.findById(id));
    }

    @Override
    public Integer insert(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public Integer delete(Integer id) {
        return userMapper.deleteUser(id);
    }
}
