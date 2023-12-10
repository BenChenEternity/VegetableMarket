package ajie.wiki.MarketManager;

import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MyBatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMyBatis() {
        // 假设数据库中有一个用户ID为1的用户
        User user = userMapper.findById(1);

        // 断言用户不为空
        assertNotNull(user);
        // 打印用户信息
        System.out.println("User ID: " + user.getId());
        System.out.println("Username: " + user.getNickname());
        // 添加其他字段的断言和打印...
    }
}
