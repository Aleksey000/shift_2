package ru.cft.starterkit.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.repository.implement.UserRepository;

import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Boolean isLogin(HttpSession session){
        Long id = (Long) session.getAttribute("user_id");

        return id != null;
    }

    public User login(String login, String password, HttpSession session){
        User user = null;
        try {
            user = userRepository.getByLogin(login);

            if (!user.getPassword().equals(password)) {
                throw new ObjectNotFoundException(String.format("Wrong password"));
            } else {
                session.setAttribute("user_id", user.getId().toString());
                return user;
            }
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    public Boolean logOut(HttpSession session){
        session.removeAttribute("user_id");
        return true;
    }

    public User getCurrentUser(HttpSession session){
        try {
            System.out.println( session.getAttribute("user_id").toString());
            Long id = Long.parseLong(session.getAttribute("user_id").toString());

            return userRepository.get(id);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
