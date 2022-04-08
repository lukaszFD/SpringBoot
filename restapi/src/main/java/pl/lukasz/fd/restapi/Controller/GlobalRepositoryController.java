package pl.lukasz.fd.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.lukasz.fd.restapi.Model.Accounts;
import pl.lukasz.fd.restapi.Service.GlobalRepositoryService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/GlobalRepository")
public class GlobalRepositoryController
{
    @Autowired
    private GlobalRepositoryService _globalRepositoryService;

    @GetMapping("/GetAccount")
    @ResponseBody
    public List<Accounts> GetAccount(@RequestParam(name = "id") Long id)
    {
        return _globalRepositoryService.GetAccount(id);
    }
}
