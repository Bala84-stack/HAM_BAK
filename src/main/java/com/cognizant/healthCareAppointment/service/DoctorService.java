package com.cognizant.healthCareAppointment.service;

import com.cognizant.healthCareAppointment.dto.AppointmentResponseDTO;
import com.cognizant.healthCareAppointment.dto.ConsultationRequest;
import com.cognizant.healthCareAppointment.dto.ConsultationResponseDTO;
import com.cognizant.healthCareAppointment.entity.Appointment;
import com.cognizant.healthCareAppointment.entity.AppointmentStatus;
import com.cognizant.healthCareAppointment.entity.Consultation;
import com.cognizant.healthCareAppointment.exception.AppointmentNotFoundException;
import com.cognizant.healthCareAppointment.repository.AppointmentRepository;
import com.cognizant.healthCareAppointment.repository.ConsultationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@Service
public class DoctorService {
    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private ConsultationRepository consultationRepo;

    public List<AppointmentResponseDTO> getDoctorAppointments( Long doctorId) {
        LocalDate today = LocalDate.now(); // .plusDays(1) for check use this 
        List<Appointment> appointments= appointmentRepo.findByDoctor_UserIdAndDate(doctorId, today);
        return appointments.stream().map(a->new AppointmentResponseDTO(a.getAppointmentId(),a.getDoctor().getName(),a.getPatient().getName(),a.getDate(),a.getTimeSlot(),a.getStatus().name())).toList();
    }

    public ResponseEntity<String> addConsultation( ConsultationRequest request) {

        Appointment appointment = appointmentRepo.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID "+request.getAppointmentId()+" not found"));

        if (appointment.getStatus() != AppointmentStatus.BOOKED) {

            return ResponseEntity.badRequest().body("Appointment already completed or invalid");
        }

        Consultation consultation = new Consultation();
        consultation.setAppointment(appointment); // setting up this for P-key for F-key in Appoitment table
        consultation.setNotes(request.getNotes());
        consultation.setPrescription(request.getPrescription());
        consultationRepo.save(consultation);

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(appointment);

        return ResponseEntity.ok("Consultation added and appointment marked as COMPLETED");
    }

    public List<ConsultationResponseDTO> getDoctorConsultations( Long doctorId) {
        List<Appointment> doctorAppointments = appointmentRepo.findByDoctor_UserId(doctorId);

        List<Long> appointmentIds = doctorAppointments.stream()
                .map(Appointment::getAppointmentId)
                .toList();

        List<Consultation>consultations= consultationRepo.findByAppointment_AppointmentIdIn(appointmentIds);
        return consultations.stream().map(con -> new ConsultationResponseDTO(con.getConsultationId(), con.getAppointment().getAppointmentId(),con.getAppointment().getPatient().getName(),con.getAppointment().getDoctor().getName(),con.getNotes(),con.getPrescription(),con.getAppointment().getDate() )).toList();
    }
}
