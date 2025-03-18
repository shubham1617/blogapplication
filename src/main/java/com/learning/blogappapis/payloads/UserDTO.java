package com.learning.blogappapis.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int id;

    @NotEmpty( message = "Should not be null")
    @Size(min = 5,max = 15)
    private String name;

    @Email( message = "Should not be null")
    private String email;

    @NotEmpty(message = "Should not be null")
    @Size(min = 5,max = 15)
    private String password;

    @NotEmpty(message = "Should not be null")
    private String about;


}
