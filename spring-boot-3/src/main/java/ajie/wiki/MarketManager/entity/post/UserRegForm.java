package ajie.wiki.MarketManager.entity.post;

import lombok.Data;

@Data
public class UserRegForm {
    private Integer id;
    private String password;
    private String nickname;
    private Boolean gender;
}
