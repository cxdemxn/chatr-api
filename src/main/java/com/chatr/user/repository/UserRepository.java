package com.chatr.user.repository;

import com.chatr.shared.repository.BaseRepository;
import com.chatr.user.model.User;

public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
