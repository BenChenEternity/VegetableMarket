package ajie.wiki.MarketManager.service;

import ajie.wiki.MarketManager.entity.response.UserInfoResponse;
import ajie.wiki.MarketManager.entity.sql.User;

public interface UserService {
    User findById(Integer id);
    UserInfoResponse findInfoById(Integer id);
    Integer insert(User user);
    Integer delete(Integer id);
}
