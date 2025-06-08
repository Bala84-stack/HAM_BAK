package com.cognizant.healthCareAppointment.exception;

import com.cognizant.healthCareAppointment.entity.Availability;

public class AvailabilityNotFoundException extends RuntimeException{
    public AvailabilityNotFoundException(String msg){
        super(msg);
    }
}
