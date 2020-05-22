package ru.hotels.rgr.dao;

import ru.hotels.rgr.model.Specification;

public interface SpecificationDao {
    Iterable<Specification> findAll();

    void save(Specification specification);

    void deleteAll();

    void delete(Specification specification);
}
