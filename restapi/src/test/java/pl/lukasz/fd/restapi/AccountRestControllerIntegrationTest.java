package pl.lukasz.fd.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.lukasz.fd.restapi.Controller.GlobalRepositoryController;
import pl.lukasz.fd.restapi.DB.AccountRepository;
import pl.lukasz.fd.restapi.Model.Accounts;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
public class AccountRestControllerIntegrationTest
{
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    AccountRepository accountRepository;

    Accounts acc1 = new Accounts(
            "190890EA-0138-4EA3-9D3F-94C5108618A0",
            1,
            141L,
            2L,
            1L,
            1L,
            "Konto_1",
            null,
            "U",
            null,
            null,
            null,
            null,
            null,
            null
    );

    @Test
    public void getAllRecords_success() throws Exception {
        Mockito.when(accountRepository.findById(acc1.getAccountId())).thenReturn(java.util.Optional.of(acc1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/GlobalRepository/GetAccount?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].externalid").value(acc1.getExternalid()));
    }
}
