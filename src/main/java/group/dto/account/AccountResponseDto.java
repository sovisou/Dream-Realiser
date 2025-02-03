package group.dto.account;

import lombok.Data;

@Data
public class AccountResponseDto {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String profileImage;
}
