package com.example.onlineWallet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onlineWallet.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
