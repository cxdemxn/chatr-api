package com.chatr.auth;

import com.chatr.auth.dto.AuthRequestDto;
import com.chatr.user.repository.UserRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    private String getRegisterUrl() {
        return "http://localhost:" + port + "/auth/register";
    }

    @BeforeEach
    void clearDb() {
        userRepository.deleteAll();
    }

//    =============================   REGISTER TESTS    ======================================
    @Nested
    class RegisterTests {
        @Nested
        class SuccessTests {
            @Test
            void shouldRegisterUserSuccessfully() {

                AuthRequestDto requestDto = new AuthRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21",
                        "en");

                ResponseEntity<String> requestResponse = restTemplate.postForEntity(getRegisterUrl(), requestDto, String.class);

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

        @Nested
        class DuplicateTests {
            @Test
//            @Disabled
            void shouldNotRegisterUserWithDuplicateEmail() {

                AuthRequestDto requestDto = new AuthRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "en");

                ResponseEntity<Void> firstResponse = restTemplate.postForEntity(getRegisterUrl(), requestDto, Void.class);
                assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                ResponseEntity<Void> secondResponse = restTemplate.postForEntity(getRegisterUrl(), requestDto, Void.class);
                assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            }
            @Test
//            @Disabled
            void shouldNotRegisterUserWithDuplicateUsername() {

                AuthRequestDto firstRequestDto = new AuthRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "en");

                ResponseEntity<Void> firstResponse = restTemplate.postForEntity(getRegisterUrl(), firstRequestDto, Void.class);
                assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                AuthRequestDto secondRequestDto = new AuthRequestDto("cxdemxn", "cxdemon@gmail.com", "cxdemxnPassword21",
                        "en");

                ResponseEntity<Void> secondResponse = restTemplate.postForEntity(getRegisterUrl(), secondRequestDto, Void.class);
                assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            }
        }

        @Nested
        class ValidationTests {
            @Test
            void shouldRejectMissingUsername() {
                AuthRequestDto requestDto = new AuthRequestDto(null, "cxdemxn@gmail.com", "cxdemxnPassword21","en");
                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto,
                        String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectInvalidEmailFormat() {
                AuthRequestDto requestDto = new AuthRequestDto("cxdemxn", "cxdemxngmail.com", "cxdemxnPassword21","en");

                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectInvalidAndEmptyPreferredLanguage() {
                AuthRequestDto invalidRequestDto = new AuthRequestDto("cxdemxn", "cxdemxn@gmail.com",
                        "cxdemxnPassword21",
                        "jp");

                ResponseEntity<String> invalidResponse = restTemplate.postForEntity(getRegisterUrl(), invalidRequestDto,
                        String.class);
                assertThat(invalidResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

                AuthRequestDto emptyRequestDto = new AuthRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21",
                        "");

                ResponseEntity<String> emptyResponse = restTemplate.postForEntity(getRegisterUrl(), emptyRequestDto,
                        String.class);
                assertThat(emptyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectUsernameWithSpaces() {
                AuthRequestDto requestDto = new AuthRequestDto("cxde mxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "gr");

                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto,
                        String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectEmailWithSpaces() {
                AuthRequestDto requestDto = new AuthRequestDto("cxdemxn", "cxde mxn@gmail.com", "cxdemxnPassword21",
                        "gr");

                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto,
                        String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
