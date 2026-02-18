package com.example.bankapp.controller;

import com.example.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    @Autowired
    private AccountService service;

    // ✅ Home page → just a menu or redirect
    @GetMapping("/")
    public String home() {
        return "create"; // Or a proper home.html if you want a dashboard/menu
    }

    // ✅ Show Create form
    @GetMapping("/create")
    public String showCreateForm() {
        return "create";
    }

    // ✅ Handle Create POST
    @PostMapping("/create")
    public String create(@RequestParam String name, @RequestParam double balance, Model model) {
        service.createAccount(name, balance);
        model.addAttribute("msg", "Account created!");
        return "result";
    }

    // ✅ Show Deposit form
    @GetMapping("/deposit")
    public String showDepositForm() {
        return "deposit";
    }

    // ✅ Handle Deposit POST
    @PostMapping("/deposit")
    public String deposit(@RequestParam long id, @RequestParam double amount, Model model) {
        service.deposit(id, amount);
        model.addAttribute("msg", "Deposited!");
        return "result";
    }

    // ✅ Show Withdraw form
    @GetMapping("/withdraw")
    public String showWithdrawForm() {
        return "withdraw";
    }

    // ✅ Handle Withdraw POST
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam long id, @RequestParam double amount, Model model) {
        service.withdraw(id, amount);
        model.addAttribute("msg", "Withdrawn!");
        return "result";
    }

    // ✅ Show Transfer form
    @GetMapping("/transfer")
    public String showTransferForm() {
        return "transfer";
    }

    // ✅ Handle Transfer POST
    @PostMapping("/transfer")
    public String transfer(@RequestParam long fromId, @RequestParam long toId, @RequestParam double amount, Model model) {
        service.transfer(fromId, toId, amount);
        model.addAttribute("msg", "Transfer done!");
        return "result";
    }

    // ✅ Show Balance form
    @GetMapping("/balance")
    public String showBalanceForm() {
        return "balance";
    }

    // ✅ Handle Balance GET
    @GetMapping("/check-balance")
    public String balance(@RequestParam long id, Model model) {
        double bal = service.getBalance(id);
        model.addAttribute("msg", "Balance: " + bal);
        return "result";
    }
}
