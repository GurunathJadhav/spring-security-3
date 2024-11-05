package com.springsecurity.controller;

import com.springsecurity.entity.Users;
import com.springsecurity.payload.LoginDto;
import com.springsecurity.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users users){
        return new ResponseEntity<>(usersService.register(users), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginDto loginDto){
        String token = usersService.login(loginDto);
        Map<String ,String > response=new HashMap<>();
        if(token!=null){
            response.put("token",token);
            return ResponseEntity.ok(response);
        }
        response.put("Error","Invalid credentials");
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public String user(){
        return "I Am User";
    }
}
