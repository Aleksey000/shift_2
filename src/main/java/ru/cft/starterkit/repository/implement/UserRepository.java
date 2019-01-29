package ru.cft.starterkit.repository.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(SampleEntityRepositoryImpl.class);

    private final AtomicLong idCounter = new AtomicLong();

    private final Map<Long, User> storage = new ConcurrentHashMap<>();

//    @Override
    public User add(User user) {
        user.setId(idCounter.incrementAndGet());
        storage.put(user.getId(), user);

        log.info("Added new sample entity to storage: {}", user);
        return user;
    }

//    @Override
    public User get(Long id) throws ObjectNotFoundException {
        User user = storage.get(id);

        if (user == null) {
            log.error("Failed to get sample entity with id '{}' from storage", id);
            throw new ObjectNotFoundException(String.format("Sample entity with id %s not found", id));
        }

        log.info("Returned sample entity with id '{}' from storage: {}", id, user);
        return user;
    }
}
