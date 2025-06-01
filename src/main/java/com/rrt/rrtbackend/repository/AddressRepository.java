package com.rrt.rrtbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.user.Address;
import com.rrt.rrtbackend.entity.user.User;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
