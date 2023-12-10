package ajie.wiki.MarketManager.entity.response;

import ajie.wiki.MarketManager.entity.sql.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInfoResponse {
    private String avatar;
    private boolean gender;
    private String email;
    private LocalDate birthday;
    private String phoneNumber;
    public UserInfoResponse(User user){
        this.avatar=user.getAvatar();
        this.gender=user.getGender();
        this.email=user.getEmail();
        this.birthday=user.getBirthday();
        this.phoneNumber=user.getPhoneNumber();
    }
}
