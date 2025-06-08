package com.cognizant.healthCareAppointment.service;

import com.cognizant.healthCareAppointment.dto.AvailabilityRequest;
import com.cognizant.healthCareAppointment.entity.Availability;
import com.cognizant.healthCareAppointment.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    public ResponseEntity<String> updateAvailability(AvailabilityRequest request) {
        Availability availability=availabilityRepository.findByDoctorIdAndDate(request.getDoctorId(), request.getDate());

        if (availability == null) {
             availability = new Availability();
        }

        availability.setDoctorId(request.getDoctorId());

            availability.setDate(request.getDate());
        availability.setIsAvailable(request.getIsAvailable());

        if (request.getIsAvailable()) {
            availability.setStartTime(request.getStartTime());
            availability.setEndTime(request.getEndTime());
        } else {
            availability.setStartTime(null);
            availability.setEndTime(null);
        }
        availabilityRepository.save(availability);
        System.out.println(availability.toString());
        return ResponseEntity.ok("Doctor Availability Updated Successfully");
    }
}
