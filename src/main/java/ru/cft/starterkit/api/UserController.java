package ru.cft.starterkit.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;
import ru.cft.starterkit.service.implement.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        try {
            return userService.login(login, password);
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // Update profile
    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/profile",
            consumes = "application/x-www-form-urlencoded",
            produces = "application/json"
    )
    public User update(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "phone") String phone
    ) {
        try {
            return userService.update(login, password, name, phone);
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
        return userService.add(login, password, name, phone);
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
        try {
            return userService.getCurrentUser();
        } catch (ObjectNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    //Logout
    @RequestMapping(
            method = RequestMethod.GET,
            path = "/logout",
            produces = "application/json"
    )
    public Boolean logOut(
    ) {
        return userService.logOut();
    }
}
