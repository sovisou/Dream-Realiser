package group.mapper;



import group.dto.account.AccountDto;
import group.dto.account.AccountResponseDto;
import group.dto.account.AccountUpdateRequestDto;
import group.model.Account;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountMapper {

    public AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setUserId(account.getUserId());
        dto.setEmail(account.getEmail());
        dto.setName(account.getName());
        dto.setPhone(account.getPhone());
        dto.setProfileImage(account.getProfileImage());
        return dto;
    }

    // Перетворення AccountDto на сутність Account
    public Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setUserId(dto.getUserId());
        account.setEmail(dto.getEmail());
        account.setName(dto.getName());
        account.setPhone(dto.getPhone());
        account.setProfileImage(dto.getProfileImage());
        return account;
    }

    // Оновлення сутності Account на основі AccountDto
    public void updateEntityFromDto(AccountUpdateRequestDto dto, Account account) {
        if (dto.getName() != null) {
            account.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            account.setPhone(dto.getPhone());
        }
        if (dto.getProfileImage() != null) {
            account.setProfileImage(dto.getProfileImage());
        }
    }
}
