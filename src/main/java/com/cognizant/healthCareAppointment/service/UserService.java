package com.cognizant.healthCareAppointment.service;
import com.cognizant.healthCareAppointment.dto.AppointmentResponseDTO;
import com.cognizant.healthCareAppointment.dto.ConsultationResponseDTO;
import com.cognizant.healthCareAppointment.dto.LoginRequest;
import com.cognizant.healthCareAppointment.dto.RegisterRequest;
import com.cognizant.healthCareAppointment.entity.Appointment;
import com.cognizant.healthCareAppointment.entity.Consultation;
import com.cognizant.healthCareAppointment.entity.User;
import com.cognizant.healthCareAppointment.repository.AppointmentRepository;
import com.cognizant.healthCareAppointment.repository.ConsultationRepository;
import com.cognizant.healthCareAppointment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepo;
    @Autowired
    private ConsultationRepository consultationRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ResponseEntity<String> register( RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        
        // Encoding the password before storing into DB
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        
        try{
            userRepository.save(user);
        }
        catch (Exception e)
        {
            log.error("Oops! Already a user");
            return ResponseEntity.badRequest().body("Email already exists");
            
        }
        log.info("User Registration Phase Run Successful");
        return ResponseEntity.ok("User registered successfully as "+ request.getRole());
    }


    public ResponseEntity<String> login( LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            log.info("User Login Phase Run Successful");
            return ResponseEntity.ok("Login successful as " + userOpt.get().getRole());
        }

        log.error("Oops! Invalid Credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    public List<ConsultationResponseDTO> getPatientConsultations( Long patientId) {
        List<Appointment> patientAppointments = appointmentRepo.findByPatient_UserId(patientId);

        List<Long> appointmentIds = patientAppointments.stream()
                .map(Appointment::getAppointmentId)
                .toList();

        List<Consultation>consultations= consultationRepo.findByAppointment_AppointmentIdIn(appointmentIds);
        return consultations.stream().map(con -> new ConsultationResponseDTO(con.getConsultationId(), con.getAppointment().getAppointmentId(),con.getAppointment().getPatient().getName(),con.getAppointment().getDoctor().getName(),con.getNotes(),con.getPrescription(),con.getAppointment().getDate() )).toList();

    }

    public List<AppointmentResponseDTO> getDoctorAppointments(Long patientId) {

       List<Appointment>appointments= appointmentRepo.findByPatient_UserId(patientId);
         return appointments.stream().map(a->new AppointmentResponseDTO(a.getAppointmentId(),a.getDoctor().getName(),a.getPatient().getName(),a.getDate(),a.getTimeSlot(),a.getStatus().name())).toList();
    }
}

