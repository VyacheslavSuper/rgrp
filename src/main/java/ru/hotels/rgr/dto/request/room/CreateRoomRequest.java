package ru.hotels.rgr.dto.request.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.request.RequestBase;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest extends RequestBase {
    @NotNull
    @Min(0)
    private int number;
    @NotNull
    @Min(0)
    private int price;
    @NotNull
    @NotBlank
    private String description;

    private List<String> specs;
}
