package ru.hotels.rgr.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hotels.rgr.dto.response.ResponseBase;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFullUserResponse extends ResponseBase {
    List<FullUserResponse> list;
}
