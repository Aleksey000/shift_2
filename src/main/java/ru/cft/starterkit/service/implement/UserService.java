package ru.cft.starterkit.service.implement;

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

    public User add(String login, String password, String name, String phone) {
        return userRepository.add(new User(login, password, name, phone, UUID.randomUUID()));
    }

    public User get(Long id) throws ObjectNotFoundException {
        return userRepository.get(id);
    }

    public User getCurrentUser() throws ObjectNotFoundException{
        return new User("","", "", "", UUID.randomUUID());
    }

    public User update(String login, String password, String name, String phone) throws ObjectNotFoundException{
        return new User("","", "", "", UUID.randomUUID());
    }

    public User login(String login, String password) throws ObjectNotFoundException {
        return new User("","", "", "", UUID.randomUUID());
    }

    public Boolean logOut(){
        return true;
    }

}
