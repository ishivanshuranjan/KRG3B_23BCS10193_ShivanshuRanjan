package com.example.healthhub.controller;

import com.example.healthhub.dto.PatientDTO;
import com.example.healthhub.entity.Patient;
import com.example.healthhub.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping("/addPatient")
    public Patient createPatient(@RequestBody PatientDTO dto){
        return service.createPatient(dto);
    }

    @GetMapping("/getPatients")
    public List<Patient> getAllPatients(){
        return service.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id){
        return service.getPatientById(id);
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable Long id){
        service.deletePatient(id);
        return "Patient Deleted";
    }
}