package com.chatr.auth;

import com.chatr.auth.dto.LoginRequestDto;
import com.chatr.auth.dto.RegisterRequestDto;
import com.chatr.user.model.User;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private String getLoginUrl() {
        return "http://localhost:" + port + "/auth/login";
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

                RegisterRequestDto requestDto = new RegisterRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21",
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

                RegisterRequestDto requestDto = new RegisterRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "en");

                ResponseEntity<Void> firstResponse = restTemplate.postForEntity(getRegisterUrl(), requestDto, Void.class);
                assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                ResponseEntity<Void> secondResponse = restTemplate.postForEntity(getRegisterUrl(), requestDto, Void.class);
                assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            }
            @Test
//            @Disabled
            void shouldNotRegisterUserWithDuplicateUsername() {

                RegisterRequestDto firstRequestDto = new RegisterRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "en");

                ResponseEntity<Void> firstResponse = restTemplate.postForEntity(getRegisterUrl(), firstRequestDto, Void.class);
                assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                RegisterRequestDto secondRequestDto = new RegisterRequestDto("cxdemxn", "cxdemon@gmail.com", "cxdemxnPassword21",
                        "en");

                ResponseEntity<Void> secondResponse = restTemplate.postForEntity(getRegisterUrl(), secondRequestDto, Void.class);
                assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            }
        }

        @Nested
        class ValidationTests {
            @Test
            void shouldRejectMissingUsername() {
                RegisterRequestDto requestDto = new RegisterRequestDto(null, "cxdemxn@gmail.com", "cxdemxnPassword21","en");
                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto,
                        String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectInvalidEmailFormat() {
                RegisterRequestDto requestDto = new RegisterRequestDto("cxdemxn", "cxdemxngmail.com", "cxdemxnPassword21","en");

                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto, String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectInvalidAndEmptyPreferredLanguage() {
                RegisterRequestDto invalidRequestDto = new RegisterRequestDto("cxdemxn", "cxdemxn@gmail.com",
                        "cxdemxnPassword21",
                        "jp");

                ResponseEntity<String> invalidResponse = restTemplate.postForEntity(getRegisterUrl(), invalidRequestDto,
                        String.class);
                assertThat(invalidResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

                RegisterRequestDto emptyRequestDto = new RegisterRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21",
                        "");

                ResponseEntity<String> emptyResponse = restTemplate.postForEntity(getRegisterUrl(), emptyRequestDto,
                        String.class);
                assertThat(emptyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectUsernameWithSpaces() {
                RegisterRequestDto requestDto = new RegisterRequestDto("cxde mxn", "cxdemxn@gmail.com", "cxdemxnPassword21", "gr");

                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto,
                        String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }

            @Test
            void shouldRejectEmailWithSpaces() {
                RegisterRequestDto requestDto = new RegisterRequestDto("cxdemxn", "cxde mxn@gmail.com", "cxdemxnPassword21",
                        "gr");

                ResponseEntity<String> response = restTemplate.postForEntity(getRegisterUrl(), requestDto,
                        String.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Nested
    class LoginTests {
        private User user;

        @BeforeEach
        void setupUser() {
            clearDb();
            RegisterRequestDto requestDto = new RegisterRequestDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21",
                        "en");

            ResponseEntity<String> requestResponse = restTemplate.postForEntity(getRegisterUrl(), requestDto, String.class);

            String username = JsonPath.parse(requestResponse.getBody()).read("$.username");

            user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " +
                    "not found"));

        }

        @Test
        void shouldLoginSuccessfully() {

            LoginRequestDto loginRequestDto = new LoginRequestDto("cxdemxn", "cxdemxnPassword21");

            ResponseEntity<String> response = restTemplate.postForEntity(getLoginUrl(), loginRequestDto, String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            DocumentContext documentContext = JsonPath.parse(response.getBody());

            Number id = documentContext.read("$.id");
            assertThat(id.longValue()).isNotNull().isEqualTo(user.getId());

            String username = documentContext.read("$.username");
            assertThat(username).isEqualTo(user.getUsername());

            String email = documentContext.read("$.email");
            assertThat(email).isEqualTo(user.getEmail());

            String preferredLanguage = documentContext.read("$.preferredLanguage");
            assertThat(preferredLanguage).isEqualTo(user.getPreferredLanguage());

            String token = documentContext.read("$.token");
            assertThat(token).isNotNull().isNotBlank();
        }

        @Test
        void shouldNotAuthorizeInvalidUsername() {
            LoginRequestDto loginRequestDto = new LoginRequestDto("codemon", "cxdemxnPassword21");

            ResponseEntity<String> response = restTemplate.postForEntity(getLoginUrl(), loginRequestDto, String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @Test
        void shouldNotAuthorizeWrongPassword() {
            LoginRequestDto loginRequestDto = new LoginRequestDto("cxdemxn", "cxdemxnPassword22");

            ResponseEntity<String> response = restTemplate.postForEntity(getLoginUrl(), loginRequestDto, String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }
}
