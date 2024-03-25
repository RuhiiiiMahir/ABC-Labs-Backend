package com.advance.programming.labappointment.controller;

import com.advance.programming.labappointment.exception.ResourceNotFoundException;
import com.advance.programming.labappointment.model.User;
import com.advance.programming.labappointment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/patients")
    public List<User> getPatients() {
        return userRepo.findByUserRole("patient");
    }

    @GetMapping("/doctors")
    public List<User> getDoctorsByRole() {
        return userRepo.findByUserRole("doctor");
    }

    @GetMapping("/laboratorian")
    public List<User> getTechByRole() {
        return userRepo.findByUserRole("laboratorian");
    }

    // Build Crate Users REST API
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    // Build Get User By Username REST API
    @PostMapping("{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
    }

    // Verify Login Details REST API
    @PostMapping("/login")
    public ResponseEntity<User> verifyLoginDetails(@RequestBody User loginUser) {
        User user = userRepo.findByUsername(loginUser.getUsername());
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {

            if (user.getUserRole().equals("patient")) {
                return ResponseEntity.ok().body(user);

            } else if (user.getUserRole().equals("doctor")) {
                return ResponseEntity.ok().body(user);

            } else if (user.getUserRole().equals("laboratorian")) {
                return ResponseEntity.ok().body(user);

            } else if (user.getUserRole().equals("admin")) {
                return ResponseEntity.ok().body(user);

            }
            return ResponseEntity.ok().body(user);
        }
        return null;
    }
}
