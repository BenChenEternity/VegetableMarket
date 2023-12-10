package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.entity.post.UserQueryParams;
import ajie.wiki.MarketManager.service.PermissionService;
import ajie.wiki.MarketManager.service.QueryUserService;
import ajie.wiki.MarketManager.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Component
@RestController
public class UserController {
    private final TokenService tokenService;
    private final QueryUserService queryUserService;
    private final PermissionService permissionService;
    @Autowired
    public UserController(TokenService tokenService, QueryUserService queryUserService, PermissionService permissionService) {
        this.tokenService = tokenService;
        this.queryUserService = queryUserService;
        this.permissionService = permissionService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> query(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody UserQueryParams userQueryParams,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        if(!tokenService.Authorization(userId,sessionToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        if (!permissionService.hasOwnerPermission(Integer.valueOf(userId))) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        HashMap<String, Object> queryResult = queryUserService.get(
                userQueryParams.getNickname(),
                userQueryParams.getGender(),
                userQueryParams.getLevel(),
                currentPage,
                pageSize
        );
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", queryResult.get("list"));
        result.put("total", queryResult.get("total"));
        return ResponseEntity.ok(result);
    }


}
