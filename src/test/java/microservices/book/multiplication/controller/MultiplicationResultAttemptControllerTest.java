package microservices.book.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Player;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
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
import org.testng.collections.Lists;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class MultiplicationResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;


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

    @Test
    public void getUserStats() throws Exception {
        //given
        Player player = new Player("john_doe");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt( player, multiplication, 3500, true);
        List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
        given(multiplicationService.getStatsForUser("john_doe")).willReturn(recentAttempts);

        // when
        MockHttpServletResponse response = mockMvc.perform( get("/results").param("alias", "john_doe")) .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResultAttemptList.write(recentAttempts).getJson());

    }

    @Test
    public void getResultByIdTest() throws Exception {
        //given
        Player player = new Player("john_doe");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt( player, multiplication, 3500, true);
        given(multiplicationService.getResultById(4L)).willReturn(attempt);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/results/4")).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResult.write(attempt).getJson());

    }

    /**
     * This test will force multiplicationservice.checkAttempt to return the passed in value (either true or false)
     * The MultiplicationResultAttempt is created then posted.  The same MultiplicationResultAttempt object should be
     * returned but with the correct field matching the passed in correct parameter.
     * @param correct this sets the forced response for
     * @see MultiplicationService#checkAttempt(MultiplicationResultAttempt) 
     */
    private void genericParameterizeTest(final boolean correct) throws Exception {
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(correct);

        Player player = new Player("John");

        Multiplication multiplication = new Multiplication(50,70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(player, multiplication, 3500, correct);

        MockHttpServletResponse response = mockMvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON).content(jsonResult.write(attempt).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo( jsonResult.write(
                new MultiplicationResultAttempt(attempt.getPlayer(),
                        attempt.getMultiplication(),
                        attempt.getResultAttempt(),
                        correct) )
                .getJson());

    }

}