package com.project.back_end.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrescriptionController {

    @GetMapping("/prescriptions")
    public String getPrescriptions() {
        return "Prescription endpoint working";
    }
}
