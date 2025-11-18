package com.chatr.shared.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailUtil {

    public String generateSignUpCode() {
        return String.format("%6d", new Random().nextInt(999999));
    }
}
