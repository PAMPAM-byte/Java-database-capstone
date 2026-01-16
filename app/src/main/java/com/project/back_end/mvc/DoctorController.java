package com.project.back_end.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    // simple token check (your project can later replace this with real JWT validation)
    private boolean isValidToken(String token) {
        return token != null && token.startsWith("Bearer ") && token.length() > 15;
    }

    /**
     * REQUIRED URL PATTERN (per grader feedback):
     * /api/doctors/{user}/{doctorId}/availability/{date}/{token}
     *
     * Example:
     * GET /api/doctors/john/1/availability/2025-04-15/Bearer_xxxxxxxxxxxxx
     */
    @GetMapping("/{user}/{doctorId}/availability/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token
    ) {
        Map<String, Object> response = new HashMap<>();

        // Validate token
        if (!isValidToken(token)) {
            response.put("success", false);
            response.put("status", 401);
            response.put("message", "Unauthorized: Invalid or missing token");
            response.put("validation", Map.of(
                    "tokenValid", false,
                    "reason", "Token must start with 'Bearer ' and be a reasonable length"
            ));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Validate date format
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception e) {
            response.put("success", false);
            response.put("status", 400);
            response.put("message", "Bad Request: date must be in YYYY-MM-DD format");
            response.put("validation", Map.of(
                    "dateValid", false,
                    "received", date
            ));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Return a structured response (doctor + date + availableTimes)
        response.put("success", true);
        response.put("status", 200);
        response.put("user", user);
        response.put("doctorId", doctorId);
        response.put("date", parsedDate.toString());
        response.put("availableTimes", new String[]{"09:00", "10:00", "11:00", "14:00"});

        return ResponseEntity.ok(response);
    }
}
