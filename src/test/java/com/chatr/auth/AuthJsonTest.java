package com.chatr.auth;

import com.chatr.auth.dto.RegisterUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class AuthJsonTest {

    @Autowired
    private JacksonTester<RegisterUserDto> json;

    @Test
    public void authJsonDeserializationTest() throws IOException {
        String expectedUser = """
                {
                    "username": "cxdemxn",
                     "email": "cxdemxn@gmail.com",
                     "password": "cxdemxnPassword21",
                     "preferredLanguage": "en"
                }
                """;

        assertThat(json.parse(expectedUser)).isEqualTo(new RegisterUserDto("cxdemxn", "cxdemxn@gmail.com", "cxdemxnPassword21",
                "en"));
    }
}
