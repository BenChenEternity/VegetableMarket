package ajie.wiki.MarketManager.service.impl;

import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.mapper.UserMapper;
import ajie.wiki.MarketManager.service.QueryUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryUserServiceImpl implements QueryUserService {
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(QueryUserServiceImpl.class);

    public QueryUserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public HashMap<String, Object> get(String nickname, Boolean gender, Integer level, Integer currentPage, Integer pageSize) {
        int offset = (currentPage - 1) * pageSize;
        Map<String, Object> queryObj = new HashMap<>();
        if (nickname != null && nickname.length() == 0) {
            nickname = null;
        }
        logger.debug(
                "\n↓↓↓↓↓↓↓↓↓↓↓↓\n" +
                "Query User parameters:\nnickname:  " +
                nickname + "\ngender:  " +
                gender + "\nlevel:  " +
                level +
                "\n↑↑↑↑↑↑↑↑↑↑↑↑"
        );
        queryObj.put("nickname", nickname);
        queryObj.put("gender", gender);
        queryObj.put("level", level);
        queryObj.put("offset", offset);
        queryObj.put("pageSize", pageSize);
        List<User> list = userMapper.getUsers(queryObj);
        Integer total = userMapper.getUserCount(queryObj);
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        return result;
    }
}
