package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    // Save / book an appointment
    public Appointment bookAppointment(Appointment appointment) {
        return appointment;
    }

    // Retrieve appointments for a doctor on a given date
    public List<Appointment> getAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        return new ArrayList<>();
    }
}
