package ru.hotels.rgr.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.hotels.rgr.dto.request.room.CreateRoomRequest;
import ru.hotels.rgr.dto.response.rooms.RoomResponse;
import ru.hotels.rgr.model.Room;

@Mapper
public interface RoomMapper {

    Room roomFromDto(CreateRoomRequest createRoomRequest);


    RoomResponse dtoFromResponse(Room room);

}
