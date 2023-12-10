package ajie.wiki.MarketManager.service;


public interface TokenService {
    boolean Authorization(String userId, String token);
    String Generate(Integer userId);

    boolean remove(String userId);
}
