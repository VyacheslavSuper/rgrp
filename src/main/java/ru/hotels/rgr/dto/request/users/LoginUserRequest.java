package ru.hotels.rgr.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.request.RequestBase;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest extends RequestBase {
    @NotNull
    @NotBlank
   // @NameValid
    private String login;
    @NotNull
    @NotBlank
   // @PasswordValid
    private String password;
}
