package ru.hotels.rgr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hotels.rgr.dao.SpecificationDao;
import ru.hotels.rgr.dao.repository.SpecificationRepository;
import ru.hotels.rgr.model.Specification;
@Component
public class SpecificationDaoImpl implements SpecificationDao {
    @Autowired
    private SpecificationRepository specificationRepository;

    @Override
    public Iterable<Specification> findAll() {
        return specificationRepository.findAll();
    }

    @Override
    public void save(Specification specification) {
        specificationRepository.save(specification);
    }

    @Override
    public void deleteAll() {
        specificationRepository.deleteAll();
    }

    @Override
    public void delete(Specification specification) {
        specificationRepository.delete(specification);
    }
}
