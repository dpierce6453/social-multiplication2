package microservices.book.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.service.MultiplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<MultiplicationResultAttempt> jsonResult;

    private JacksonTester<MultiplicationResultAttemptController.ResultResponse> jsonResponse;

    @BeforeEach
    public void Setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception {
        genericParameterizeTest(true);
    }


    @Test
    public void postResultReturnNotCorrect() throws Exception {
        genericParameterizeTest(false);
    }

    private void genericParameterizeTest(final boolean b) throws Exception {
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(b);

        User user = new User("John");

        Multiplication multiplication = new Multiplication(50,70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, false);

        MockHttpServletResponse response = mockMvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON).content(jsonResult.write(attempt).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResponse.write(new MultiplicationResultAttemptController.ResultResponse(b)).getJson());
    }

}