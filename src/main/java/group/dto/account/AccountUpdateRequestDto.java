package group.dto.account;

import lombok.Data;

@Data
public class AccountUpdateRequestDto {
    private String name;
    private String phone;
    private String profileImage;
}
