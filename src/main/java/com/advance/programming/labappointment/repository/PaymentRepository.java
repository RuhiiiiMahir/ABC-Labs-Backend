package com.advance.programming.labappointment.repository;

import com.advance.programming.labappointment.model.Appointment;
import com.advance.programming.labappointment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByAppointment(Appointment appointment);

    Payment findByAppointment_AppointmentID(Long appointmentId);
}
