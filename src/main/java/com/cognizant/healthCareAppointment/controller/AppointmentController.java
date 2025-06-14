package com.cognizant.healthCareAppointment.controller;

import com.cognizant.healthCareAppointment.dto.BookRequest;
import com.cognizant.healthCareAppointment.dto.DoctorInfoDTO;
import com.cognizant.healthCareAppointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired 
    private AppointmentService appointmentService;
    @PreAuthorize("hasRole('Patient')")
	  @GetMapping("/available-doctors") 
	  public List<DoctorInfoDTO> getAvailableDoctorsForTomorrow() {
	  return appointmentService.getAvailableDoctorsForTomorrow(); 
	  }
	  
	  
	/*
	 * @GetMapping("/available-doctors") public String
	 * getAvailableDoctorsForTomorrow() {
	 * 
	 * return "NO Doctors "; }
	 */
	  
	  
	@PreAuthorize("hasRole('Patient')")
    @GetMapping("/doctor/{doctorId}/timeslots")
    public List<String> getAvailableSlots(@PathVariable Long doctorId) {

        return appointmentService.getAvailableSlots(doctorId);
    }
	
	@PreAuthorize("hasRole('Patient')")
    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody BookRequest request, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        return appointmentService.bookAppointment(request);
    }
    
	@PreAuthorize("hasRole('Patient')")
    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId){
        return appointmentService.cancelAppointment(appointmentId);
    }
}