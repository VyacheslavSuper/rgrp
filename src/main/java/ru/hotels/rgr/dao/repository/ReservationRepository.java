package ru.hotels.rgr.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hotels.rgr.model.Reservation;
import ru.hotels.rgr.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    @Modifying
    void deleteAll();

    List<Reservation> findAll();

    Page<Reservation> findAll(Pageable pageable);
}
