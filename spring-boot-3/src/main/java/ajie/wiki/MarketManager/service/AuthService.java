package ajie.wiki.MarketManager.service;

import ajie.wiki.MarketManager.entity.post.UserCredentials;
import ajie.wiki.MarketManager.entity.post.UserRegForm;

import java.util.HashMap;

public interface AuthService {
    HashMap<String,String> login(UserCredentials userCredentials);

    HashMap<String, String> register(UserRegForm userRegForm);

    boolean logout(String userId, String sessionToken);
}
