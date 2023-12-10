package ajie.wiki.MarketManager.entity.post;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoForm {
    private Integer id;
    private boolean gender;
    private String email;
    private String avatar;
    private String phoneNumber;
    private Date Birthday;
}
