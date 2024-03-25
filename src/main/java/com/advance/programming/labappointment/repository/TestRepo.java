package com.advance.programming.labappointment.repository;

import com.advance.programming.labappointment.model.Test;
import com.advance.programming.labappointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepo extends JpaRepository<Test,Long> {

    List<Test> getByUserId(User user);
}
