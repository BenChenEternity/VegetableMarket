package ajie.wiki.MarketManager.controller;

import ajie.wiki.MarketManager.Tool.DateFormatter;
import ajie.wiki.MarketManager.entity.post.VegetableQueryParams;
import ajie.wiki.MarketManager.service.QueryVegetableService;
import ajie.wiki.MarketManager.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@Component
@RestController
public class VegetableController {
    private final TokenService tokenService;
    private final QueryVegetableService queryVegetableService;

    @Autowired
    public VegetableController(TokenService tokenService, QueryVegetableService queryVegetableService) {
        this.tokenService = tokenService;
        this.queryVegetableService = queryVegetableService;
    }

    @PostMapping("/vegetable")
    public ResponseEntity<?> query(
            @RequestHeader(value = "user-id", required = false) String userId,
            @RequestHeader(value = "session-token", required = false) String sessionToken,
            @RequestBody VegetableQueryParams vegetableQueryParams,
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize) {
        if(!tokenService.Authorization(userId,sessionToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session Timeout.");
        }
        LocalDate productionDate = DateFormatter.format(vegetableQueryParams.getProductionDate());
        LocalDate shelfDate = DateFormatter.format(vegetableQueryParams.getShelfDate());
        HashMap<String, Object> queryResult = queryVegetableService.get(
                vegetableQueryParams.getName(),
                productionDate,
                vegetableQueryParams.getShelfLife(),
                shelfDate,
                vegetableQueryParams.getPrice(),
                vegetableQueryParams.getStock(),
                vegetableQueryParams.getSupplier(),
                vegetableQueryParams.getSale(),
                currentPage,
                pageSize
        );
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", queryResult.get("list"));
        result.put("total", queryResult.get("total"));
        return ResponseEntity.ok(result);
    }
}
