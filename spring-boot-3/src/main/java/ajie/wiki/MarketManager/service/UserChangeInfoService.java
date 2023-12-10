package ajie.wiki.MarketManager.service;

import ajie.wiki.MarketManager.entity.post.UserInfoForm;

public interface UserChangeInfoService {
    boolean change(Integer userId, UserInfoForm userInfoForm);

    //100原密码错误 200修改成功 300修改失败
    int modifyPassword(Integer userId, String originPasswordInput, String password);
}
