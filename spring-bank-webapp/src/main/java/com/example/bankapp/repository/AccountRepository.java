package com.example.bankapp.repository;

import com.example.bankapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Procedure(procedureName = "transfer_amount")
    void transferAmount(Long from_account, Long to_account, Double amount);

    @Query(value = "SELECT get_balance(?1)", nativeQuery = true)
    Double getBalance(Long accountId);
}
