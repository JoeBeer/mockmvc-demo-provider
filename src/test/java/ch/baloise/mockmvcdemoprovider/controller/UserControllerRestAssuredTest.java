package ch.baloise.mockmvcdemoprovider.controller;

import ch.baloise.mockmvcdemoprovider.model.User;
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
public class UserControllerRestAssuredTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testGetUserWhenUserExists() {
        String userId = "345";
        // Provider State: "User exists"
        // Set up the user in the in-memory store
        userController.createUser(userId, new User(userId, "John Doe"));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/users/{userId}", userId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(userId))
                .body("name", equalTo("John Doe"));
    }

    @Test
    void testGetUserWhenUserDoesNotExist() {
        String userId = "999";
        // Provider State: "User does not exist"
        // Ensure the user is not in the store
        userController.getUserStore().remove(userId);

        RestAssuredMockMvc
                .given()
                .when()
                .get("/users/{userId}", userId)
                .then()
                .statusCode(404);
    }

    @Test
    void testUpdateUser() {
        String userId = "123";
        // Provider State: "User exists and can be updated"
        // Set up the user in the in-memory store
        userController.createUser(userId, new User(userId, "Old Name"));

        // Prepare the updated user data (note that id is null as per the Pact file)
        User updatedUser = new User(null, "Jane Doe");

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body(updatedUser)
                .when()
                .put("/users/{userId}", userId)
                .then()
                .statusCode(204);

        // Verify that the user was updated
        RestAssuredMockMvc
                .given()
                .when()
                .get("/users/{userId}", userId)
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("name", equalTo("Jane Doe"));
    }
}
