package com.project.back_end.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private boolean isValidToken(String token) {
        return token != null && token.startsWith("Bearer ") && token.length() > 15;
    }

    @GetMapping("/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam String specialty,
            @RequestParam String date
    ) {
        if (!isValidToken(authorization)) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", 401);
            error.put("message", "Unauthorized: Invalid or missing token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        LocalDate parsedDate = LocalDate.parse(date);

        List<LocalTime> availableTimes = Arrays.asList(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0)
        );

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("specialty", specialty);
        response.put("date", parsedDate.toString());
        response.put("availableTimes", availableTimes);

        return ResponseEntity.ok(response);
    }
}
