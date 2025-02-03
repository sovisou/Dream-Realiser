package group.controller;

import group.dto.account.AccountDto;
import group.dto.account.AccountResponseDto;
import group.dto.account.AccountUpdateRequestDto;
import group.exception.EntityNotFoundException;
import group.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<AccountDto> getAccount(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();

        AccountDto accountDto = accountService.getAccount(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountDto> updateAccount(
            @AuthenticationPrincipal Jwt jwt, @RequestBody AccountUpdateRequestDto updateRequest) {

        String userId = jwt.getSubject();

        AccountDto updatedAccount = accountService.updateAccount(userId, updateRequest);

        return ResponseEntity.ok(updatedAccount);
    }
}
