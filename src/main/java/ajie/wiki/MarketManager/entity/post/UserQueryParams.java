package ajie.wiki.MarketManager.entity.post;

import lombok.Data;

@Data
public class UserQueryParams {
    private Integer id;
    private String nickname;
    private Boolean gender;
    private Integer level;
}
