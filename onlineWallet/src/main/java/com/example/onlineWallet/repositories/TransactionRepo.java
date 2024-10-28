package com.example.onlineWallet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineWallet.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
