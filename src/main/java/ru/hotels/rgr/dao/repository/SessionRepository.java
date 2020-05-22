package ru.hotels.rgr.dao.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hotels.rgr.model.Session;
import ru.hotels.rgr.model.User;

import java.util.Optional;
@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

    @Modifying
    void deleteSessionByUser(@Param("user") User user);

    Optional<Session> findByCookie(String cookie);

    Session findByUser(User user);

    @Modifying
    void deleteAll();

}
