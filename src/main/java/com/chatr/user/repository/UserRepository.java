package com.chatr.user.repository;

import com.chatr.shared.repository.BaseRepository;
import com.chatr.user.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
