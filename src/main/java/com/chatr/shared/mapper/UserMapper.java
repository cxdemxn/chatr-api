package com.chatr.shared.mapper;

import com.chatr.shared.enums.PreferredLanguage;
import com.chatr.user.dto.UserDto;
import com.chatr.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), PreferredLanguage.fromCode(user.getPreferredLanguage()).getCode());
    }
}
