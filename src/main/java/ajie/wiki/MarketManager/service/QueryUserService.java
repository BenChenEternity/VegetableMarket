package ajie.wiki.MarketManager.service;

import java.util.HashMap;

public interface QueryUserService {
    HashMap<String, Object> get(String nickname, Boolean gender, Integer level, Integer currentPage, Integer pageSize);
}
