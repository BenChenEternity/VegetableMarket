package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.entity.post.UserInfoForm;
import ajie.wiki.MarketManager.mapper.UserMapper;
import ajie.wiki.MarketManager.service.UserChangeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserChangeInfoServiceImpl implements UserChangeInfoService {
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserChangeInfoServiceImpl.class);

    public UserChangeInfoServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean change(Integer userId, UserInfoForm userInfoForm) {
        userInfoForm.setId(userId);
        return userMapper.updateInfo(userInfoForm);
    }

    //100原密码错误
    //200修改成功
    //300修改失败
    @Override
    public int modifyPassword(Integer userId, String originPasswordInput, String newPassword) {
        String originPassword = userMapper.findById(userId).getPassword();
        if (!originPassword.equalsIgnoreCase(originPasswordInput)) {
            return 100;
        }
        HashMap<String, Object> updatePasswordObject = new HashMap<>();
        updatePasswordObject.put("id", userId);
        updatePasswordObject.put("password", newPassword);
        int result = userMapper.updatePassword(updatePasswordObject);
        logger.info(userId+"  修改密码");
        return (result > 0) ? 200 : 300;
    }
}
