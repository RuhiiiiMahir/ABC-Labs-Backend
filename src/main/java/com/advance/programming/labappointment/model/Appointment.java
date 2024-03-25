package com.advance.programming.labappointment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AppointmentID")
    private Long appointmentID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Appointment_Date")
    private Date appointmentDate;

    @Column(name = "Appointment_Time")
    private String appointmentTime;

    @Column(name = "Test_Type")
    private String testType;

    @Column(name = "Assigned_Technician")
    private String assignedTechnician;

    @Column(name = "Recommending_Doctor")
    private String recommendingDoctor;
}
