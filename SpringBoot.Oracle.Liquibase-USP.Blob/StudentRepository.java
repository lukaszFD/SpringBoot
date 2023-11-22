package com.example.repository;

import com.example.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.sql.Blob;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Procedure(name = "p_dodaj_blob")
    void dodajBlob(Long id, Blob blob);
}
