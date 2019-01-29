package ru.cft.starterkit.service;

import ru.cft.starterkit.entity.SampleEntity;
import ru.cft.starterkit.exception.ObjectNotFoundException;

public interface SampleEntityService {

    SampleEntity add(String foo, Double bar);

    SampleEntity get(Long id) throws ObjectNotFoundException;

}
