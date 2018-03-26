package com.leave.ws;

import com.leave.entity.User;
import com.leave.service.JwtService;
import com.leave.service.UserService;
import com.leave.utils.CustomStatus;
import com.leave.ws.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserRest {
    private final static Logger LOGGER = Logger.getLogger(UserRest.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<User> getAll() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return null;
        }
        return users;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String password, @RequestParam String email) throws RestException {
        if (userService.isExist(email)) {
            LOGGER.log(Level.SEVERE, "Unable to create. A User with name " + email + " already exist.");
            throw new RestException("Unable to create. A User with name " + email + " already exist.", HttpStatus.BAD_REQUEST, CustomStatus.ACCOUNT_EXIST);
        }
        userService.create(firstname, lastname, password, email);
    }
}