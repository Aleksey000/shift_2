package ru.cft.starterkit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.service.implement.AuthService;
import ru.cft.starterkit.service.implement.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    private HttpServletRequest request;

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // Login
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/login",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public User login(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password
            ) {
        return authService.login(login, password, this.request.getSession());
    }

    // Update profile
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/profile",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public User update(
//            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "phone") String phone
    ) {
        try {
            User user = authService.getCurrentUser(this.request.getSession());
//            user.setLogin(login);
            user.setName(name);
            user.setPhone(phone);
            user.setPassword(password);

            return userService.update(user);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // Create profile
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/profile",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public User add(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "phone") String phone
    ) {


        try {
            return userService.add(login, password, name, phone);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage(), e);
        }
    }

    // View other profile
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/profile/{id}",
            produces = "application/json"
    )
    public User get(
            @PathVariable(name = "id") Long id
    ) {
        try {
            return userService.get(id);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // View current user profile
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/profile",
            produces = "application/json"
    )
    public User getMyProfile(
    ) {
        return authService.getCurrentUser(this.request.getSession());
    }

    //Logout
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/logout",
            produces = "application/json"
    )
    public HashMap<String, Boolean> logOut(
    ) {
        HashMap<String, Boolean> answer = new HashMap<>();
        answer.put("isOk", authService.logOut(this.request.getSession()));
        return answer;
    }
}
