package pl.lukasz.fd.restapi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukasz.fd.restapi.DB.IGlobalRepository;
import pl.lukasz.fd.restapi.Model.Accounts;
import pl.lukasz.fd.restapi.Model.Student;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class GlobalRepositoryService
{
    @Autowired
    private IGlobalRepository _globalRepository;

    public List<Accounts> GetAccount()
    {
        return _globalRepository.findAll();
    }
}
