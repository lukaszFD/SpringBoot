package pl.lukasz.fd.restapi.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/GlobalRepository")
public class GlobalRepositoryController
{
    @GetMapping("/hello")
    public String hello()
    {
        return "Hi Lukasz";
    }
}
