package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.exceptions.ResourceNotFoundException;
import com.learning.blogappapis.model.User;
import com.learning.blogappapis.payloads.UserDTO;
import com.learning.blogappapis.repository.UserRepo;
import com.learning.blogappapis.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepo userRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User savedUser = this.userRepo.save(user);
        return this.userToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","User Id",id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAbout(userDTO.getAbout());
        User savedUser = this.userRepo.save(user);
        UserDTO updatedUserDTO = this.userToDTO(savedUser);
        return updatedUserDTO;

    }

    @Override
    public UserDTO getUserById(int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","User Id",id));
        UserDTO userDTO = this.userToDTO(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUser = this.userRepo.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : allUser) {
            userDTOS.add(this.userToDTO(user));
        }
        return userDTOS;
    }

    @Override
    public void deleteUser(int id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", id));
        this.userRepo.delete(user);
    }

   /* //Manually converting DTO to USER type
    private User dtoToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setAbout(userDTO.getAbout());
        return user;
    }*/

    private User dtoToUser(UserDTO userDTO){
        User mapDtoToUser = this.modelMapper.map(userDTO, User.class);
        return mapDtoToUser;
    }

/*    //Manually Converting USER to DTO type
    private UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());
        return userDTO;
    }*/

    private UserDTO userToDTO(User user){
        UserDTO mapUserToUser = this.modelMapper.map(user, UserDTO.class);
        return mapUserToUser;
    }
}
