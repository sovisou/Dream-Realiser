package group.dto.user;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String email;
    private String password;
}
