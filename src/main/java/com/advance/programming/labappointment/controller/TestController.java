package com.advance.programming.labappointment.controller;

import com.advance.programming.labappointment.model.Test;
import com.advance.programming.labappointment.model.User;
import com.advance.programming.labappointment.repository.TestRepo;
import com.advance.programming.labappointment.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tests")
@CrossOrigin(origins = "*")
public class TestController {
    private final TestRepo testRepo;
    private final UserRepo userRepo;

    @Autowired
    public TestController(TestRepo testRepo, UserRepo userRepo) {
        this.testRepo = testRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Test> getAllTest() {
        return testRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test savedTest = testRepo.save(test);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTest);

    }

    @GetMapping("/{userId}")
    public List<Test> getByPatient(@PathVariable Long userId){

        User user = userRepo.getUserById(userId);
        return testRepo.getByUserId(user);
    }
}
