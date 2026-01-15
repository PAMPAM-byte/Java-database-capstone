package com.project.back_end.services;

import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    public String getAvailability() {
        return "Available";
    }
}
