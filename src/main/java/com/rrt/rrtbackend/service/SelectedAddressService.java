package com.rrt.rrtbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.user.Address;
import com.rrt.rrtbackend.entity.user.SelectedAddress;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.entity.user.UserAddress;
import com.rrt.rrtbackend.repository.AddressRepository;
import com.rrt.rrtbackend.repository.SelectedAddressRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

@Service
public class SelectedAddressService {

    @Autowired
    private SelectedAddressRepository selectedAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public User getUserFromToken(String token) {
        Long userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void selectAddress(String token, Long addressId) {
        User user = getUserFromToken(token);
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        SelectedAddress selected = selectedAddressRepository.findByUser(user).orElse(new SelectedAddress());
        selected.setUser(user);
        selected.setAddress(address);
        selectedAddressRepository.save(selected);
    }

    public UserAddress getSelectedAddress(String token) {
        User user = getUserFromToken(token);
        return selectedAddressRepository.findByUser(user)
                .map(selected -> new UserAddress(selected.getAddress()))
                .orElse(null);
    }
}
