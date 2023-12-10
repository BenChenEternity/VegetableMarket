package ajie.wiki.MarketManager.entity.sql;

import lombok.Data;

@Data
public class Owner {
    private Integer id;
    private String password;
    private String authToken;
}

