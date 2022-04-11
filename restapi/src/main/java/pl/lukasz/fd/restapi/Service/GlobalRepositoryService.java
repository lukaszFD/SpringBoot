package pl.lukasz.fd.restapi.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.lukasz.fd.restapi.DB.AccountRepository;
import pl.lukasz.fd.restapi.Model.Accounts;
import java.util.Collections;
import java.util.List;

@Service
public class GlobalRepositoryService
{
    @Autowired
    private AccountRepository _accountRepository;

    public List<Accounts> GetAccount(Integer id)
    {
        return _accountRepository.findAllById(Collections.singleton(id));
    }
}
