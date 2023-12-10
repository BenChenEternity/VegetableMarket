package ajie.wiki.MarketManager.service;

public interface PermissionService {
    boolean hasManagerPermission(Integer userId);
    boolean hasOwnerPermission(Integer userId);
}
