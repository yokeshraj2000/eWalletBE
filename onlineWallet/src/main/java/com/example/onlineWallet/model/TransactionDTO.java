package com.example.onlineWallet.model;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionDTO {
    private Long fromUserId;
    private Long toUserId;
    private String toUserName;
    private Double amount;
    private Date createdAt;

    public TransactionDTO(Long fromUserId, Long toUserId, String toUserName, Double amount, Date createdAt) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.amount = amount;
        this.createdAt = createdAt;
    }
}
