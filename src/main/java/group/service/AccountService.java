package group.service;

import group.dto.account.AccountDto;
import group.dto.account.AccountUpdateRequestDto;

import java.util.Optional;

public interface AccountService {
    Optional<AccountDto> getAccount(String userId);

    AccountDto updateAccount(String userId, AccountUpdateRequestDto updateRequest);

}
