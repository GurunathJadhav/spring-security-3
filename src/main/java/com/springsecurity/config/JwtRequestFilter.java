package com.springsecurity.config;

import com.springsecurity.entity.Users;
import com.springsecurity.repository.UsersRepository;
import com.springsecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UsersRepository usersRepository;
    private JwtService jwtService;

    public JwtRequestFilter(UsersRepository usersRepository, JwtService jwtService) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            Optional<Users> user = usersRepository.findByUsername(jwtService.getUsername(token));
            if(user.isPresent()){
                Users users = user.get();
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(users,null, Collections.singleton(new SimpleGrantedAuthority(users.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }



        }
        filterChain.doFilter(request,response);
    }
}
