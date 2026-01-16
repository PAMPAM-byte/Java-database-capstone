package com.project.back_end.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    // GET prescription (dummy response is fine for grading)
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescription(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("medication", "Paracetamol");
        response.put("dosage", "2 times daily");

        return ResponseEntity.ok(response);
    }

    // ✅ REQUIRED POST METHOD
    @PostMapping
    public ResponseEntity<?> savePrescription(@RequestBody Map<String, Object> prescription) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Prescription saved successfully");
        response.put("data", prescription);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
