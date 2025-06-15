package com.rrt.rrtbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrt.rrtbackend.entity.user.UserAddress;
import com.rrt.rrtbackend.service.SelectedAddressService;

@RestController
@RequestMapping("/api/user/selected-address")
@CrossOrigin(origins = "*")
public class SelectedAddressController {

    @Autowired
    private SelectedAddressService selectedAddressService;

    @PostMapping("/select/{addressId}")
    public ResponseEntity<String> selectAddress(@RequestHeader("Authorization") String token,
                                                @PathVariable Long addressId) {
        selectedAddressService.selectAddress(token.substring(7), addressId);
        return ResponseEntity.ok("Address selected");
    }

    @GetMapping
    public ResponseEntity<UserAddress> getSelectedAddress(@RequestHeader("Authorization") String token) {
        UserAddress address = selectedAddressService.getSelectedAddress(token.substring(7));
        return ResponseEntity.ok(address);
    }
}
