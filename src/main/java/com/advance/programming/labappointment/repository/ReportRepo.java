package com.advance.programming.labappointment.repository;

import com.advance.programming.labappointment.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepo extends JpaRepository<Report,Long> {

}
