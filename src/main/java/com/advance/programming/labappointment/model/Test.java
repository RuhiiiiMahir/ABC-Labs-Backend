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
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TestID")
    private Long testID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Test_Type")
    private String testType;

    @Column(name = "Doctor_Name")
    private String doctorName;

    @Column(name = "Assign_Date")
    private Date assignDate;
}
