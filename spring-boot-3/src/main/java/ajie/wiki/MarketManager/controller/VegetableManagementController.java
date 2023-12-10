package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.Tool.IdWrapper;
import ajie.wiki.MarketManager.entity.sql.User;
import ajie.wiki.MarketManager.entity.sql.Vegetable;
import ajie.wiki.MarketManager.mapper.UserMapper;
import ajie.wiki.MarketManager.mapper.VegetableMapper;
import ajie.wiki.MarketManager.service.PermissionService;
import ajie.wiki.MarketManager.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/vegetable")
public class VegetableManagementController {
    private final TokenService tokenService;
    private final PermissionService permissionService;
    private final VegetableMapper vegetableMapper;
    private final UserMapper userMapper;

    public VegetableManagementController(TokenService tokenService, PermissionService permissionService, VegetableMapper vegetableMapper, UserMapper userMapper) {
        this.tokenService = tokenService;
        this.permissionService = permissionService;
        this.vegetableMapper = vegetableMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody Vegetable vegetable) {
        User user = userMapper.findById(Integer.valueOf(userId));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!tokenService.Authorization(userId, sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        if (!permissionService.hasManagerPermission(Integer.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        int insertLines = vegetableMapper.insertVegetable(vegetable);
        if (insertLines == 1) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.ok("Failed");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody IdWrapper idWrapper) {
        User user = userMapper.findById(Integer.valueOf(userId));
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!tokenService.Authorization(userId, sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        if (!permissionService.hasManagerPermission(Integer.valueOf(userId))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        int deleteLines = vegetableMapper.deleteVegetable(idWrapper.getId());
        if (deleteLines == 1) {
            return ResponseEntity.ok("删除成功" + idWrapper.getId());
        } else {
            return ResponseEntity.ok("删除失败");
        }
    }
}
