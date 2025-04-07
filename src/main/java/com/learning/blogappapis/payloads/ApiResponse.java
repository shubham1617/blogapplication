package com.learning.blogappapis.payloads;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse
{
    private String message;
    private boolean success;
    private LocalDateTime date;
    private UserDTO userDTO;

    public ApiResponse(String message, boolean success, LocalDateTime date)
    {
        this.message = message;
        this.success = success;
        this.date = date;
    }

    public ApiResponse(UserDTO userDTO)
    {
        this.userDTO=userDTO;
    }

}
