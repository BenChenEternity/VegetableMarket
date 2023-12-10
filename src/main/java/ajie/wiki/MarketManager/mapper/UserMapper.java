package ajie.wiki.MarketManager.mapper;

import ajie.wiki.MarketManager.entity.post.UserInfoForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ajie.wiki.MarketManager.entity.sql.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    User findById(@Param("id") Integer id);

    User findByNickname(@Param("nickname") String nickname);

    int insertUser(User user);
    int deleteUser(Integer id);

    List<User> getUsers(Map<String, Object> queryObj);

    Integer getUserCount(Map<String, Object> queryObj);

    boolean updateInfo(UserInfoForm userInfoForm);

    int updatePassword(HashMap<String, Object> updatePasswordObject);
}

