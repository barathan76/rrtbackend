package com.rrt.rrtbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrt.rrtbackend.entity.User;
import com.rrt.rrtbackend.entity.authentication.AuthenticatedDevice;

import java.util.List;
import java.util.Optional;

public interface AuthenticatedDeviceRepository extends JpaRepository<AuthenticatedDevice, Long> {
    Optional<AuthenticatedDevice> findByToken(String token);

    List<AuthenticatedDevice> findByUser(User user);

    void deleteByToken(String token);

}
