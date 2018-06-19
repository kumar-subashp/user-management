package com.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.model.User;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserManagementIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void getAllUsers() throws JSONException, IOException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> serviceResponse = mapper.readValue(response.getBody(), List.class);
        assertEquals(1, serviceResponse.size());

    }

    @Test
    public void getUser() throws JSONException, IOException {

        User user = buildUser();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/" + user.getSsn()),
                HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();

        User serviceResponse = mapper.readValue(response.getBody(), User.class);

        assertEquals(user.getSsn(), serviceResponse.getSsn());
    }

    @Test
    public void addUser() throws JSONException {

        User user = buildUser();

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users"),
                HttpMethod.POST, entity, String.class);

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains(user.getSsn()));

    }

    private User buildUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        user.setLastName("test");
        user.setSsn("111-11-1111");
        return user;
    }

    @Test
    public void deleteUser() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/111-11-1111"),
                HttpMethod.DELETE, entity, String.class);

        String expected = "[]";

        assertEquals("true", response.getBody());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}