package ajie.wiki.MarketManager.entity.sql;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    private String password;
    private String nickname;
    private Boolean gender;
    private String email;
    private String avatar;
    private String phoneNumber;
    private LocalDate birthday;
    private Integer level;
}

