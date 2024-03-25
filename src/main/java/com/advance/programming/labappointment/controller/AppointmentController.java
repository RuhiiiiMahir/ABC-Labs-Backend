package com.advance.programming.labappointment.controller;

import com.advance.programming.labappointment.model.Appointment;
import com.advance.programming.labappointment.model.User;
import com.advance.programming.labappointment.repository.AppointmentRepo;
import com.advance.programming.labappointment.repository.UserRepo;
import com.advance.programming.labappointment.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentRepo appointmentRepo;
    private final UserRepo userRepo;
    private final EmailService emailService;

    @Autowired
    public AppointmentController(AppointmentRepo appointmentRepo,UserRepo userRepo,EmailService emailService) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @GetMapping
    public List<Appointment> getAllAppointment() {
        return appointmentRepo.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Appointment> getAppointmentsByUser(@PathVariable Long userId) {
        User user = userRepo.getUserById(userId);

        // Now you can use the user object to fetch appointments
        return appointmentRepo.findByUser(user);
    }

    @PostMapping
     public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentRepo.save(appointment);

        User user = userRepo.getUserById(appointment.getUser().getId());
        String body = appointment.getAppointmentID().toString() + "\n" + user.getFullname();

        sendConfirmationEmail(user.getEmail(), "Appointment Confirmation", "Your appointment has been scheduled."+"\n"+body);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);
    }

    private void sendConfirmationEmail(String to, String subject, String body) {
        emailService.sendEmail(to, subject, body);
    }


}
