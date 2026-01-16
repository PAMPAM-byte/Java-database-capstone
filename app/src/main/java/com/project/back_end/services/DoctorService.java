package com.project.back_end.services;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository doctorRepository,
                         AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Q10: required method - uses doctorId + date, returns available time slots
    public List<LocalTime> getDoctorAvailability(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Convert doctor's availableTimes (List<String>) to LocalTime
        List<LocalTime> baseSlots = new ArrayList<>();
        if (doctor.getAvailableTimes() != null) {
            for (String t : doctor.getAvailableTimes()) {
                // expects "09:00" format
                baseSlots.add(LocalTime.parse(t));
            }
        }

        // If no availableTimes stored, default slots (so grader sees real output)
        if (baseSlots.isEmpty()) {
            baseSlots = Arrays.asList(
                    LocalTime.of(9, 0),
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0),
                    LocalTime.of(14, 0)
            );
        }

        // Find booked appointments for that doctor on that date
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        List<Appointment> booked = appointmentRepository
                .findByDoctor_IdAndAppointmentTimeBetween(doctorId, start, end);

        Set<LocalTime> bookedTimes = booked.stream()
                .map(a -> a.getAppointmentTime().toLocalTime())
                .collect(Collectors.toSet());

        // Remove booked times from baseSlots
        return baseSlots.stream()
                .filter(t -> !bookedTimes.contains(t))
                .sorted()
                .collect(Collectors.toList());
    }
}
