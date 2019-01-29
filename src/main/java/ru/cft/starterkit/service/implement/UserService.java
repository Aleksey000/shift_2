package ru.cft.starterkit.service.implement;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.repository.implement.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User add(String login, String password, String name, String phone) throws Exception {
        try {
            userRepository.getByLogin(login);
            throw new Exception("User already exist!");
        } catch (ObjectNotFoundException e) {
            return userRepository.add(new User(login, password, name, phone, UUID.randomUUID()));
        }
    }

    public User get(Long id) throws ObjectNotFoundException {
        return userRepository.get(id);
    }

    public User update(User user) throws ObjectNotFoundException{
        userRepository.update(user);
        return user;
    }


}
