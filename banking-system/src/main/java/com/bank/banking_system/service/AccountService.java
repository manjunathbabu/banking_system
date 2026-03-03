package com.bank.banking_system.service;

import com.bank.banking_system.entity.Account;
import com.bank.banking_system.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final com.bank.banking_system.repository.AccountRepository accountRepository;

    public Account createAccount(Account account) {
        account.setBalance(account.getBalance() == null ? 0.0 : account.getBalance());
        account.setStatus("ACTIVE");
        account.setCreatedDate(LocalDateTime.now());
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public void freezeAccount(String accountNumber) {
        Account account = getAccount(accountNumber);
        account.setStatus("FROZEN");
        accountRepository.save(account);
    }
}