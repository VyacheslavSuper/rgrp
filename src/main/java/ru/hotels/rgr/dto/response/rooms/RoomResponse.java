package ru.hotels.rgr.dto.response.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse extends ResponseBase {
    private int id;
    private int number;
    private int price;
    private String description;
    private List<String> spec;
}
