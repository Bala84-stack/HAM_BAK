package com.cognizant.healthCareAppointment.repository;

import com.cognizant.healthCareAppointment.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByDate(LocalDate date);
    Availability findByDoctorIdAndDate(Long doctorId, LocalDate date);

}