package ru.hotels.rgr.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends ResponseBase {
    private long id;
    private String login;
    private String email;
}
