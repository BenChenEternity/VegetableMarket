package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.Tool.IdWrapper;
import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.service.PermissionService;
import ajie.wiki.MarketManager.service.TokenService;
import ajie.wiki.MarketManager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/user")
public class UserManageController {
    private final TokenService tokenService;
    private final PermissionService permissionService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VegetableController.class);

    public UserManageController(TokenService tokenService, PermissionService permissionService, UserService userService) {
        this.tokenService = tokenService;
        this.permissionService = permissionService;
        this.userService = userService;
    }


    @PostMapping("/insert")
    public ResponseEntity<?> insertUser(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody User userInsert) {
        if(!tokenService.Authorization(userId,sessionToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        User user = userService.findById(Integer.valueOf(userId));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!permissionService.hasManagerPermission(Integer.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Integer result = userService.insert(userInsert);
        return (result > 0) ?ResponseEntity.ok("插入成功" + userInsert.getNickname()): ResponseEntity.ok("插入失败" + userInsert.getNickname());
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody IdWrapper idWrapper) {
        if (!tokenService.Authorization(userId, sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        if (!permissionService.hasOwnerPermission(Integer.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.findById(Integer.valueOf(userId));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!permissionService.hasManagerPermission(Integer.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (idWrapper.getId() == 1) {
            logger.warn(userId + "  试图删除上帝管理员");
            return ResponseEntity.ok("不能删除上帝管理员");
        }
        Integer result = userService.delete(idWrapper.getId());
        return (result > 0) ?ResponseEntity.ok("删除成功" + idWrapper.getId()): ResponseEntity.ok("删除失败" + idWrapper.getId());
    }
}
