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
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID")
    private Long reportID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "Report_Date")
    private Date reportDate;

    @Column(name = "Report_File_Path")
    private String reportFilePath;

}
