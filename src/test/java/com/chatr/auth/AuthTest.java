package com.chatr.auth;

import com.chatr.auth.dto.AuthRequestDto;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRegisterUrl() {
        return "http://localhost:" + port + "/auth/register";
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        String url = getRegisterUrl();

        AuthRequestDto request = new AuthRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "en");

        ResponseEntity<String> requestResponse = restTemplate.postForEntity(url, request, String.class);

        assertThat(requestResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        DocumentContext documentContext = JsonPath.parse(requestResponse.getBody());

        Number id = documentContext.read("$.id");
        String username = documentContext.read("$.username");
        String email = documentContext.read("$.email");
        String preferredLanguage = documentContext.read("$.preferredLanguage");

        assertThat(id).isNotNull();
        assertThat(username).isEqualTo("cxdemxn");
        assertThat(email).isEqualTo("cxdemxn@gmail.com");
        assertThat(preferredLanguage).isEqualTo("en");
    }
}
