package group.dto.user;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRegistrationResponseDto {
    private String accessToken;
    private String refreshToken;
    private String idToken;

}
