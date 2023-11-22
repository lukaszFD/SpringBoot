package com.example.controller;

import com.example.model.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable Long id, @RequestBody Student student) {
        // Update logic
    }

    @PutMapping("/addBlob/{id}")
    public void addBlob(@PathVariable Long id, @RequestBody byte[] data) {
        Student student = studentRepository.findById(id).orElseThrow();
        try {
            Blob blob = new SerialBlob(data);
            studentRepository.dodajBlob(id, blob);
        } catch (SQLException e) {
            // Handle exception
        }
    }
}
