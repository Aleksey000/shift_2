package ru.cft.starterkit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.service.implement.AuthService;
import ru.cft.starterkit.service.implement.UserService;

@RestController
public class UserController {

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
        return authService.login(login, password);
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
            User user = authService.getCurrentUser();
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
        return authService.getCurrentUser();
    }

    //Logout
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/logout",
            produces = "application/json"
    )
    public String logOut(
    ) {
        // TODO Сделать норм ответ
        return "{isOk: " + (authService.logOut() == true ? "true" : "false")  + "}";
    }
}
