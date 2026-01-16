package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Q10: must retrieve doctor's availability based on doctorId + date
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Doctor's default availability slots (from Doctor.availableTimes)
        List<String> availableTimes = doctor.getAvailableTimes();

        // Get appointments for that doctor on that date
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay(); // exclusive
        List<Appointment> appointments =
                appointmentRepository.findByDoctor_IdAndAppointmentTimeBetween(doctorId, start, end);

        // Remove booked slots from availableTimes
        Set<String> bookedSlots = new HashSet<>();
        for (Appointment appt : appointments) {
            if (appt.getAppointmentTime() != null) {
                bookedSlots.add(appt.getAppointmentTime().toLocalTime().toString());
            }
        }

        // Keep only the free slots
        availableTimes.removeIf(bookedSlots::contains);

        return availableTimes;
    }
}
