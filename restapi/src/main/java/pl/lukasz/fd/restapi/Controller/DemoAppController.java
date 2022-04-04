package pl.lukasz.fd.restapi.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lukasz.fd.restapi.Model.Student;
import pl.lukasz.fd.restapi.Service.DemoAppService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/DemoApp")
public class DemoAppController
{
    private final DemoAppService _appService;

    @Autowired
    public DemoAppController(DemoAppService appService)
    {
        _appService = appService;
    }

    @GetMapping("/GetStudent")
    public List<Student> GetStudent()
    {
        return _appService.GetStudent();
    }
}
