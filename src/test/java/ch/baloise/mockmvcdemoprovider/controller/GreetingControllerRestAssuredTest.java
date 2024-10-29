package ch.baloise.mockmvcdemoprovider.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GreetingController greetingController;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testGreeting() {
        RestAssuredMockMvc
                .given()
                .when()
                .get("/greeting")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("message", equalTo("Hello, World!"));
    }
}
