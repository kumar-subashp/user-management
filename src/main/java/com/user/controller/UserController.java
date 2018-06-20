package com.user.controller;

import com.user.model.User;
import com.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Api(value = "user-management", description = "User Management supported operations")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "View a list of available users", response = List.class)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(@RequestParam(value = "firstName", required = false) String firstName) {

        return userService.getAllUsers(firstName);
    }

    @ApiOperation(value = "Get User details by SSN Number")
    @RequestMapping(value = "/users/{ssn}", method = RequestMethod.GET)
    public User getUser(@PathVariable String ssn) {

        return userService.getUser(ssn);
    }

    @ApiOperation(value = "Add a user")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@Valid @RequestBody User user) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{ssn}").buildAndExpand(userService.addUser(user)).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Delete a user")
    @RequestMapping(value = "/users/{ssn}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteUser(@Valid @PathVariable String ssn) {

        userService.deleteUser(ssn);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
    }
}
