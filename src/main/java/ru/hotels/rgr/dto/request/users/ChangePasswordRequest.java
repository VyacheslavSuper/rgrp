package ru.hotels.rgr.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.request.RequestBase;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest extends RequestBase {
    @NotNull
    @NotBlank
   // @PasswordValid
    private String oldPassword;
    @NotNull
    @NotBlank
   // @PasswordValid
    private String password;
}
