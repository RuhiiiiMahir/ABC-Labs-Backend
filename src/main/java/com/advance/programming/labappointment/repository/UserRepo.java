package com.advance.programming.labappointment.repository;

import com.advance.programming.labappointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);

    User getUserById(Long id);

    List<User> findByUserRole(String patient);
}
