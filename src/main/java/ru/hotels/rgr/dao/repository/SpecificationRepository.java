package ru.hotels.rgr.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.hotels.rgr.model.Specification;
import ru.hotels.rgr.model.User;
@Repository
public interface SpecificationRepository extends CrudRepository<Specification, Long> {
}
