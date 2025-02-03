package group.dto.account;

import lombok.Data;

@Data
public class AccountDto {
    private String userId;
    private String email;
    private String name;
    private String phone;
    private String profileImage;
}
