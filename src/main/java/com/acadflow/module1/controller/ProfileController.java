package com.acadflow.module1.controller;

import com.acadflow.module1.dto.UserCreateDTO;
import com.acadflow.module1.entity.User;
import com.acadflow.module1.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<User> createProfile(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(profileService.createProfile(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        return profileService.getProfile(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(profileService.updateProfile(id, dto));
    }
}
