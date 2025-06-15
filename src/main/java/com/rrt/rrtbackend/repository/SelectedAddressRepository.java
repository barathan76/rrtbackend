package com.rrt.rrtbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.user.SelectedAddress;
import com.rrt.rrtbackend.entity.user.User;

public interface SelectedAddressRepository extends JpaRepository<SelectedAddress, Long> {
    Optional<SelectedAddress> findByUser(User user);
}
