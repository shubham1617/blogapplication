package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.exceptions.ResourceNotFoundException;
import com.learning.blogappapis.model.Roles;
import com.learning.blogappapis.model.User;
import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.UserDTO;
import com.learning.blogappapis.repository.RoleRepo;
import com.learning.blogappapis.repository.UserRepo;
import com.learning.blogappapis.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepo userRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private RoleRepo roleRepo;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JWTServiceImpl jwtService;

    @Override
    public UserDTO createUser(UserDTO userDTO)  {
        User user = this.dtoToUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        // Fetch or create the roles (example "READ" role)
        Roles readRole = roleRepo.findByRoleName("READ");  // Assuming you have a method to find roles by name
        // If the "READ" role doesn't exist, create it
        if (readRole == null) {
            readRole = new Roles();
            readRole.setRoleName("READ");
            roleRepo.save(readRole);  // Save the new role
        }
        user.setRoles(Collections.singletonList(readRole));
        User existingUser = userRepo.findByEmailCaseSensitive(user.getEmail());
        if(!(existingUser == null)){
            return userDTO; //todo:check for user already exist in the database
        }
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
    public UserDTO getUserByEmail(String email) {
        User userByUsername = this.userRepo.findByEmailCaseSensitive(email);
        return this.userToDTO(userByUsername);
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

    @Override
    public String verifyUser(UserDTO userDTO) {
        //User userByEmail = this.userRepo.findByEmailCaseSensitive(userDTO.getEmail());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        if(Objects.isNull(authenticate)){
            return "Failure";
        }
        return jwtService.generateToken(userDTO);
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
