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

    public User add(User user) {
        user.setId(idCounter.incrementAndGet());
        storage.put(user.getId(), user);

        log.info("Added new user to storage: {}", user);
        return user;
    }

    public User update(User user) throws ObjectNotFoundException {
        if (user == null) {
            throw new ObjectNotFoundException(String.format("User not found"));
        }
        storage.put(user.getId(), user);

        log.info("Update user to storage: {}", user);
        return user;
    }

    public User get(Long id) throws ObjectNotFoundException {
        User user = storage.get(id);

        if (user == null) {
            log.error("Failed to get user with id '{}' from storage", id);
            throw new ObjectNotFoundException(String.format("User with id %s not found", id));
        }

        log.info("Returned user with id '{}' from storage: {}", id, user);
        return user;
    }

    public User getByLogin(String login) throws ObjectNotFoundException {
        User user = null;
        for(Map.Entry<Long, User> entry: storage.entrySet()) {
            if(entry.getValue().getLogin().equals(login)) {
                return entry.getValue();
            }
        }

        if (user == null) {
            log.error("Failed to get user with login '{}' from storage", login);
            throw new ObjectNotFoundException(String.format("User with login %s not found", login));
        }

        return user;
    }
}
