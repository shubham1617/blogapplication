package com.learning.blogappapis.service;


import com.learning.blogappapis.payloads.UserDTO;

import java.util.List;

public interface UserService
{

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, int id);
    UserDTO getUserById(int id);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    void deleteUser(int id);
    String verifyUser(UserDTO userDTO);
}
