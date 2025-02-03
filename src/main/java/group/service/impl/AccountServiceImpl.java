package group.service.impl;

import group.dto.account.AccountDto;
import group.dto.account.AccountResponseDto;
import group.dto.account.AccountUpdateRequestDto;
import group.exception.EntityNotFoundException;
import group.mapper.AccountMapper;
import group.model.Account;
import group.repository.AccountRepository;
import group.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Optional<AccountDto> getAccount(String userId) {
        return accountRepository.findByUserId(userId)
                .map(accountMapper::toDto);

    }

    @Override
    public AccountDto updateAccount(String userId, AccountUpdateRequestDto updateRequest) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        accountMapper.updateEntityFromDto(updateRequest, account);

        accountRepository.save(account);
        return accountMapper.toDto(account);
    }
}
