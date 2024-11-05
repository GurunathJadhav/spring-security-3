package com.springsecurity.service;

import com.springsecurity.entity.Users;
import com.springsecurity.payload.LoginDto;
import com.springsecurity.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    private JwtService jwtService;

    public UsersService(UsersRepository usersRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
    }

    public Users register(Users user){
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        return usersRepository.save(user);
    }


    public String login(LoginDto loginDto){
        Optional<Users> users = usersRepository.findByUsername(loginDto.getUsername());
        if(users.isPresent()){
            Users user = users.get();
            if(BCrypt.checkpw(loginDto.getPassword(),user.getPassword())){
                return jwtService.generateToken(user);
            }

        }
        return null;

    }
}
