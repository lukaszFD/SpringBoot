package pl.lukasz.fd.restapi.Service;

import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukasz.fd.restapi.DB.IGlobalRepository;
import pl.lukasz.fd.restapi.Model.Accounts;
import java.util.Collections;
import java.util.List;

@Service
public class GlobalRepositoryService
{
    @Autowired
    private IGlobalRepository _globalRepository;

    public List<Accounts> GetAccount(Long id)
    {
        return _globalRepository.findAllById(Collections.singleton(id));
    }
}