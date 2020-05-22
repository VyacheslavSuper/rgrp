package ru.hotels.rgr.dto.response.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;
import ru.hotels.rgr.model.Room;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListRoomResponse extends ResponseBase {
    private List<RoomResponse> list;
}
