package com.advance.programming.labappointment.repository;

import com.advance.programming.labappointment.model.Appointment;
import com.advance.programming.labappointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    List<Appointment> findByUser(User user);

}
