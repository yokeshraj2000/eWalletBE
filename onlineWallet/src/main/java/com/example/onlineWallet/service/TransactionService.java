package com.example.onlineWallet.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlineWallet.model.Transaction;
import com.example.onlineWallet.model.TransactionDTO;
import com.example.onlineWallet.model.User;
import com.example.onlineWallet.repositories.TransactionRepo;
import com.example.onlineWallet.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public Transaction createTransaction(Transaction request) {
        User sender = userRepo.findById(request.getFromUserId()).orElse(null);
        User receiver = userRepo.findById(request.getToUserId()).orElse(null);

        if (sender == null || receiver == null || sender.getWalletBalance() < request.getAmount()) {
            throw new IllegalArgumentException("Invalid transaction request");
        }

        sender.setWalletBalance(sender.getWalletBalance() - request.getAmount());
        receiver.setWalletBalance(receiver.getWalletBalance() + request.getAmount());
        userRepo.save(sender);
        
        userRepo.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setFromUserId(request.getFromUserId());
        transaction.setToUserId(request.getToUserId());
        transaction.setAmount(request.getAmount());
        transaction.setCreatedAt(new Date());

        return transactionRepo.save(transaction);
    }

    public List<TransactionDTO> getTransactionHistory(Long userId) {
        List<Transaction> transactions = transactionRepo.findByFromUserIdOrToUserId(userId, userId);

        List<TransactionDTO> transactionDTOs = transactions.stream().map(transaction -> {
            User toUser = userRepo.findById(transaction.getToUserId()).orElse(null);
            String toUserName = toUser != null ? toUser.getUsername() : "Unknown User";
            return new TransactionDTO(
                    transaction.getFromUserId(),
                    transaction.getToUserId(),
                    toUserName,
                    transaction.getAmount(),
                    transaction.getCreatedAt());
        }).collect(Collectors.toList());

        return transactionDTOs;
    }

}
