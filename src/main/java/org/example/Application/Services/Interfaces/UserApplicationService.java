package org.example.Application.Services.Interfaces;

import org.example.Application.DTOs.UserDTO;

import java.util.Optional;

/*
En este caso quedo igual que el de domain,
pero al momento de ahrehar otro metodo en esta interface toma sentido
 */
public interface UserApplicationService {
    void follow(String followerUsername, String followedUsername);
    UserDTO findByUsername(String username);
    UserDTO findByUsernameFollowingEager(String username);
}
