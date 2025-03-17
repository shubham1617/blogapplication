package com.learning.blogappapis.service;


import com.learning.blogappapis.model.User;
import com.learning.blogappapis.payloads.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, int id);
    UserDTO getUserById(int id);
    List<UserDTO> getAllUsers();
    void deleteUser(int id);

}
