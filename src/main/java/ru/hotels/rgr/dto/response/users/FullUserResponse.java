package ru.hotels.rgr.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.model.types.UserType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullUserResponse extends ResponseBase {
    private long id;
    private String login;
    private String name;
    private String email;
    private UserType userType;
    private LocalDate timeRegistered;
    private Boolean deleted;

}
