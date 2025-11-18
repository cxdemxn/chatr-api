package com.chatr.shared.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void cacheVerificationCode(String email, String code) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(email, code, 15, TimeUnit.MINUTES);
    }

    @Override
    public boolean verifyCode(String email, String code) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        Object storedCode = operations.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            redisTemplate.delete(email);
            return true;
        }

        return false;
    }
}
