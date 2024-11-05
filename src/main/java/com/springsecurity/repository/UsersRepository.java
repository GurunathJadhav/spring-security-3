package com.springsecurity.repository;

import com.springsecurity.entity.Users;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("select u from Users u where u.email=:username")
    Optional<Users> findByUsername(@PathParam("username") String username);
}