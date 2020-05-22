package ru.hotels.rgr.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.request.RequestBase;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest extends RequestBase {
    @NotNull
    @NotBlank
    private String login;
    private String name;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String password;

    public RegisterUserRequest(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }
}
