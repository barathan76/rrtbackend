package com.rrt.rrtbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrt.rrtbackend.entity.user.UserAddress;
import com.rrt.rrtbackend.service.AddressService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<UserAddress> getAllAddresses(@RequestHeader("Authorization") String token) {
        return addressService.getAllAddresses(token);
    }

    @PostMapping
    public UserAddress addAddress(@RequestBody UserAddress address, @RequestHeader("Authorization") String token) {
        return addressService.addAddress(token, address);
    }

    @PutMapping("/{id}")
    public UserAddress updateAddress(@PathVariable Long id,
            @RequestBody UserAddress address,
            @RequestHeader("Authorization") String token) {
        return addressService.updateAddress(id, address, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        addressService.deleteAddress(id, token);
        return ResponseEntity.ok("Address deleted");
    }
}
