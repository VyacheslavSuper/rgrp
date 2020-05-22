package ru.hotels.rgr.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hotels.rgr.model.Reservation;
import ru.hotels.rgr.model.Room;
import ru.hotels.rgr.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findByNumber(int number);

    Page<Room> findAll(Pageable pageable);

    List<Room> findAll();

    @Modifying
    void deleteAll();

}
