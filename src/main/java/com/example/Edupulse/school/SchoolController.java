package com.example.Edupulse.school;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {

    @GetMapping("/sh")
    public ResponseEntity<String> school() {
        return ResponseEntity.ok().body("school");
    }



}
