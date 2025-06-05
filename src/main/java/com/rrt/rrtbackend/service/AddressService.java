package com.rrt.rrtbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.user.Address;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.entity.user.UserAddress;
import com.rrt.rrtbackend.repository.AddressRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public List<UserAddress> getAllAddresses(String token) {
        User user = getUserFromToken(token);
    return addressRepository.findByUser(user).stream().map(UserAddress::new).collect(Collectors.toList());
        
    }

    public UserAddress addAddress(String token, UserAddress userAddress) {
        User user = getUserFromToken(token);
        Address address = new Address(userAddress);
        address.setUser(user);
        return new UserAddress(addressRepository.save(address));
    }

    public UserAddress updateAddress(Long id, UserAddress updatedAddress, String token) {
        User user = getUserFromToken(token);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        // Update only the fields you want
        address.setName(updatedAddress.getName());
        address.setNumber(updatedAddress.getNumber());
        address.setPincode(updatedAddress.getPincode());
        address.setState(updatedAddress.getState());
        address.setCity(updatedAddress.getCity());
        address.setAddressLine1(updatedAddress.getAddressLine1());
        address.setAddressLine2(updatedAddress.getAddressLine2());
        address.setAddressType(updatedAddress.getAddressType());
        address.setLandMark(updatedAddress.getLandMark());

        return new UserAddress(addressRepository.save(address));
    }

    @Transactional
    public void deleteAddress(Long id, String token) {
        User user = getUserFromToken(token);
        System.err.println(id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        addressRepository.delete(address);
    }

    private User getUserFromToken(String token) {
        Long userId = jwtUtil.extractUserId(token.replace("Bearer ", ""));
        return userRepository.findById(userId).orElseThrow();
    }
}
