package pl.lukasz.fd.restapi.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.lukasz.fd.restapi.Model.Student;

import java.time.LocalDate;
import java.util.List;

@Service
//@Component = @Service
public class DemoAppService
{
    public List<Student> GetStudent()
    {
        return List.of(
                new Student(
                        1L,
                        "Lukas",
                        "lukasz.f.d@gmail.com",
                        LocalDate.of(2022, 4, 5),
                        38)
        );
    }
}
