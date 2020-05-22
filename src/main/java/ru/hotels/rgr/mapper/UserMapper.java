package ru.hotels.rgr.mapper;

import org.mapstruct.Mapper;
import ru.hotels.rgr.dto.request.users.RegisterUserRequest;
import ru.hotels.rgr.dto.response.users.FullUserResponse;
import ru.hotels.rgr.dto.response.users.UserResponse;
import ru.hotels.rgr.model.User;

@Mapper
public interface UserMapper {

    User userFromDto(RegisterUserRequest request);

    UserResponse dtoFromUser(User user);

    FullUserResponse fullDtoFromUser(User user);

}
