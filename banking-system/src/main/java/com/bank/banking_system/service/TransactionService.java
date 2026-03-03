package com.bank.banking_system.service;

import com.bank.banking_system.entity.Account;
import com.bank.banking_system.entity.Transaction;
import com.bank.banking_system.repository.AccountRepository;
import com.bank.banking_system.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void deposit(String accountNumber, Double amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getStatus().equals("ACTIVE"))
            throw new RuntimeException("Account is frozen");

        account.setBalance(account.getBalance() + amount);

        transactionRepository.save(Transaction.builder()
                .transactionType("DEPOSIT")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .toAccount(accountNumber)
                .build());
    }

    @Transactional
    public void withdraw(String accountNumber, Double amount) {

        if (amount == null || amount <= 0)
            throw new RuntimeException("Withdraw amount must be greater than zero");

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getStatus().equals("ACTIVE"))
            throw new RuntimeException("Account is frozen");

        if (account.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");

        account.setBalance(account.getBalance() - amount);

        transactionRepository.save(Transaction.builder()
                .transactionType("WITHDRAW")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .fromAccount(accountNumber)
                .build());
    }

    @Transactional
    public void transfer(String fromAcc, String toAcc, Double amount) {

        if (amount == null || amount <= 0)
            throw new RuntimeException("Transfer amount must be greater than zero");

        Account sender = accountRepository.findByAccountNumber(fromAcc)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account receiver = accountRepository.findByAccountNumber(toAcc)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        transactionRepository.save(Transaction.builder()
                .transactionType("TRANSFER")
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .fromAccount(fromAcc)
                .toAccount(toAcc)
                .build());
    }
}