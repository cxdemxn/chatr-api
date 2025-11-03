package com.chatr.user.repository;

import com.chatr.shared.repository.BaseRepository;
import com.chatr.user.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
