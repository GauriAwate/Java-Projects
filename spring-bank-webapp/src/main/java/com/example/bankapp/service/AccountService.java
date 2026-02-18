package com.example.bankapp.service;

import com.example.bankapp.model.Account;
import com.example.bankapp.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void createAccount(String name, Double balance) {
        Account acc = new Account();
        acc.setName(name);
        acc.setBalance(BigDecimal.valueOf(balance));
        repo.save(acc);
    }

    @Transactional
    public void deposit(Long id, Double amount) {
        Account acc = repo.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        acc.setBalance(acc.getBalance().add(BigDecimal.valueOf(amount)));
        repo.save(acc);
    }

    @Transactional
    public void withdraw(Long id, Double amount) {
        Account acc = repo.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (acc.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        acc.setBalance(acc.getBalance().subtract(BigDecimal.valueOf(amount)));
        repo.save(acc);
    }

    public void transfer(Long fromId, Long toId, Double amount) {
        repo.transferAmount(fromId, toId, amount);
    }

    public Double getBalance(Long id) {
        return repo.getBalance(id);
    }
}
