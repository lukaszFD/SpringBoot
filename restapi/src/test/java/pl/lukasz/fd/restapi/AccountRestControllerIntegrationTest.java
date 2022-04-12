package pl.lukasz.fd.restapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.lukasz.fd.restapi.Model.Accounts;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc()
public class AccountRestControllerIntegrationTest
{
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAccount() throws Exception
    {
        Accounts acc1 = new Accounts();
        acc1.setExternalid("190890EA-0138-4EA3-9D3F-94C5108618A0");
        acc1.setAccountId(1);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/GlobalRepository/GetAccount?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$[0].externalid").value(acc1.getExternalid()));
    }
}
