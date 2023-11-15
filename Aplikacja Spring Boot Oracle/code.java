// Student.java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // getters and setters
}

// StudentRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

// StudentService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public void saveStudents(List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            studentRepository.save(students.get(i));

            // commit every 10000 rows
            if (i > 0 && i % 10000 == 0) {
                studentRepository.flush();
                studentRepository.clear();
            }
        }
    }
}

// MainApplication.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Sample data
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            Student student = new Student();
            student.setName("Student " + i);
            students.add(student);
        }

        // Save students to the database
        studentService.saveStudents(students);
    }
}
