package com.rrt.rrtbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrt.rrtbackend.entity.user.Address;
import com.rrt.rrtbackend.entity.user.User;
import com.rrt.rrtbackend.repository.AddressRepository;
import com.rrt.rrtbackend.repository.UserRepository;
import com.rrt.rrtbackend.utility.JwtUtil;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Address> getAllAddresses(String token) {
        User user = getUserFromToken(token);
        return addressRepository.findByUser(user);
    }

    public Address addAddress(String token, Address address) {
        User user = getUserFromToken(token);
        address.setUser(user);
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, Address updatedAddress, String token) {
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

        return addressRepository.save(address);
    }

    public void deleteAddress(Long id, String token) {
        User user = getUserFromToken(token);
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
