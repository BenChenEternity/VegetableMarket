package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.entity.post.UserCredentials;
import ajie.wiki.MarketManager.entity.post.UserRegForm;
import ajie.wiki.MarketManager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserCredentials userCredentials) {
        System.out.println(userCredentials.getUsername());
        return authService.login(userCredentials);
    }

    @PostMapping("/register")
    public HashMap<String, String> register(@RequestBody UserRegForm userRegForm) {
        System.out.println(userRegForm.getNickname());
        return authService.register(userRegForm);
    }

    @PostMapping("/logout")
    public boolean register(@RequestHeader(value = "user-id", required = false) String userId,
                            @RequestHeader(value = "session-token", required = false) String sessionToken) {
        if(userId==null||sessionToken==null){
            return false;
        }
        return authService.logout(userId, sessionToken);
    }
}
