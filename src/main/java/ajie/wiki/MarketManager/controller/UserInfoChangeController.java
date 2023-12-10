package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.Tool.SHA256;
import ajie.wiki.MarketManager.entity.post.PasswordChangeForm;
import ajie.wiki.MarketManager.entity.post.UserInfoForm;
import ajie.wiki.MarketManager.service.TokenService;
import ajie.wiki.MarketManager.service.UserChangeInfoService;
import ajie.wiki.MarketManager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class UserInfoChangeController {
    private final TokenService tokenService;
    private final UserChangeInfoService userChangeInfoService;
    private final UserService userService;

    public UserInfoChangeController(TokenService tokenService, UserChangeInfoService userChangeInfoService, UserService userService) {
        this.tokenService = tokenService;
        this.userChangeInfoService = userChangeInfoService;
        this.userService = userService;
    }

    @PostMapping("/change-info")
    public ResponseEntity<?> change(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody UserInfoForm userInfoForm) {
        if (!tokenService.Authorization(userId, sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        if (userChangeInfoService.change(Integer.valueOf(userId), userInfoForm)) {
            return ResponseEntity.ok("修改成功");
        } else {
            return ResponseEntity.ok("修改失败");
        }
    }

    @PostMapping("/info")
    public ResponseEntity<?> info(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken) {
        if (!tokenService.Authorization(userId, sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        return ResponseEntity.ok(userService.findInfoById(Integer.valueOf(userId)));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody PasswordChangeForm passwordChangeForm) {
        if (!tokenService.Authorization(userId, sessionToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        return ResponseEntity.ok(userChangeInfoService.modifyPassword(Integer.valueOf(userId), SHA256.hash(passwordChangeForm.getOriginPassword()), passwordChangeForm.getNewPassword()));
    }


}
