package com.learning.blogappapis.controller;

import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.UserDTO;
import com.learning.blogappapis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired private UserService userService;

    //create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        UserDTO createdUser = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO){
        /*UserDTO userByEmail = this.userService.getUserByEmail(userDTO.getEmail());
        if(Objects.isNull(userByEmail)){
            return "Failure";
        }
        return "Success";*/
        return userService.verifyUser(userDTO);
    }



    //update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @Valid @RequestBody UserDTO userDTO)
    {
        UserDTO userDTO1 = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(userDTO1,HttpStatus.OK);
    }

    //get single User
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getSingleUser(@PathVariable int id)
    {
        UserDTO userById = this.userService.getUserById(id);
        if(userById != null)
        {
            return new ResponseEntity<>(userById,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(userById,HttpStatus.NOT_FOUND);
    }

    //get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUser()
    {
        List<UserDTO> allUsers = this.userService.getAllUsers();
        if(allUsers != null)
        {
            return new ResponseEntity<>(allUsers,HttpStatus.FOUND);
        }
        return new ResponseEntity<>(allUsers,HttpStatus.NOT_FOUND);

    }


    //delete user
    @PreAuthorize("hasRole('ADMIN')") //using EnableMethodSecurity to configure manual method security
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id)
    {
        LocalDateTime currentDate = LocalDateTime.now();
        this.userService.deleteUser(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true,currentDate),HttpStatus.OK);
    }
}
