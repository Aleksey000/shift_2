package ru.cft.starterkit.repository.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.cft.starterkit.entity.SampleEntity;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.repository.SampleEntityRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SampleEntityRepositoryImpl implements SampleEntityRepository {

    private static final Logger log = LoggerFactory.getLogger(SampleEntityRepositoryImpl.class);

    private final AtomicLong idCounter = new AtomicLong();

    private final Map<Long, SampleEntity> storage = new ConcurrentHashMap<>();

    @Override
    public SampleEntity add(SampleEntity sampleEntity) {
        sampleEntity.setId(idCounter.incrementAndGet());
        storage.put(sampleEntity.getId(), sampleEntity);

        log.info("Added new sample entity to storage: {}", sampleEntity);
        return sampleEntity;
    }

    @Override
    public SampleEntity get(Long id) throws ObjectNotFoundException {
        SampleEntity sampleEntity = storage.get(id);

        if (sampleEntity == null) {
            log.error("Failed to get sample entity with id '{}' from storage", id);
            throw new ObjectNotFoundException(String.format("Sample entity with id %s not found", id));
        }

        log.info("Returned sample entity with id '{}' from storage: {}", id, sampleEntity);
        return sampleEntity;
    }

}
