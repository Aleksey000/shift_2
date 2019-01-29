package ru.cft.starterkit.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.repository.implement.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Boolean isLogin(){
        return true;
    }

    public User login(String login, String password){
        User user = null;
        try {
            user = userRepository.getByLogin(login);

            if (!user.getPassword().equals(password)) {
                throw new ObjectNotFoundException(String.format("Wrong password"));
            } else {
                return user;
            }
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    public Boolean logOut(){
        return true;
    }

    public User getCurrentUser(){
        try {
            return userRepository.get(1l);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
