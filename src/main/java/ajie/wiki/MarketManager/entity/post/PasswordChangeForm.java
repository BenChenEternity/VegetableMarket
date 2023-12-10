package ajie.wiki.MarketManager.entity.post;

import lombok.Data;

@Data
public class PasswordChangeForm {
    String originPassword;
    String newPassword;
}
