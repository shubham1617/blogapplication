package com.learning.blogappapis.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO
{
    private int id;
    @NotEmpty(message = "Should not be blank")
    private String categoryTitle;
    @NotEmpty(message = "Should not be blank")
    @Size(min = 10,max = 150)
    private String categoryDescription;
}
