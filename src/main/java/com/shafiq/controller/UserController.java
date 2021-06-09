package com.shafiq.controller;

import com.shafiq.model.Role;
import com.shafiq.model.User;
import com.shafiq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody User user){

        if(userService.findByUsername(user.getUsername())!=null)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal)
    {
        if(principal == null)
        {
            return ResponseEntity.ok(principal);
        }
        return new ResponseEntity<>(userService.findByUsername(principal.getName()),HttpStatus.OK);
    }


}
