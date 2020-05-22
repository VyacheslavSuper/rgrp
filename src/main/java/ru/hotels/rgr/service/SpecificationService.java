package ru.hotels.rgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hotels.rgr.dao.SpecificationDao;
import ru.hotels.rgr.model.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpecificationService extends ServiceBase {
    @Autowired
    public SpecificationService() {
    }

    public List<Specification> createSpecificationListFromStringList(List<String> stringSpec) {

        List<Specification> listSpec = new ArrayList<>();
        if (stringSpec == null || stringSpec.isEmpty()) {
            return listSpec;
        }
        stringSpec.forEach(sp -> {
            Specification specification = new Specification(sp);
            specificationDao.save(specification);
            listSpec.add(specification);
        });
        return listSpec;
    }

    public List<String> createStringListFromSpecificationList(List<Specification> specificationList) {
        List<String> specStrings = new ArrayList<>();
        specificationList.forEach(a -> specStrings.add(a.getName()));
        return specStrings;
    }


}
