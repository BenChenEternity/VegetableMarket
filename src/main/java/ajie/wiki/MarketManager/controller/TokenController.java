package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.service.TokenService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class TokenController {
    private final TokenService tokenService;
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/check")
    public boolean check(@RequestHeader(value = "user-id", required = false) String userId,
                         @RequestHeader(value = "session-token", required = false) String sessionToken) {
        if(userId!=null&&sessionToken!=null){
            return tokenService.Authorization(userId, sessionToken);
        }else{
            return false;
        }
    }
}
