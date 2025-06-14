package com.cognizant.healthCareAppointment.controller;

import com.cognizant.healthCareAppointment.dto.AppointmentResponseDTO;
import com.cognizant.healthCareAppointment.dto.ConsultationResponseDTO;
import com.cognizant.healthCareAppointment.dto.LoginRequest;
import com.cognizant.healthCareAppointment.dto.RegisterRequest;
import com.cognizant.healthCareAppointment.repository.AppointmentRepository;
import com.cognizant.healthCareAppointment.repository.ConsultationRepository;
import com.cognizant.healthCareAppointment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
        }
        return userService.register(request);
    }

	/*
	 * @PostMapping("/login") public ResponseEntity<String>
	 * login(@Valid @RequestBody LoginRequest request, BindingResult result) {
	 * if(result.hasErrors()){ return
	 * ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
	 * } return userService.login(request);
	 
    }*/
    
    @PreAuthorize("hasRole('Patient')")
    @GetMapping("/{patientId}/appointments")
    public List<AppointmentResponseDTO> getDoctorAppointments(@PathVariable Long patientId) {

        return userService.getDoctorAppointments(patientId);
    }
    
    @PreAuthorize("hasRole('Patient')")
    @GetMapping("/{patientId}/consultations")
    public List<ConsultationResponseDTO> getPatientConsultations(@PathVariable Long patientId) {

        return userService.getPatientConsultations(patientId);
    }
}