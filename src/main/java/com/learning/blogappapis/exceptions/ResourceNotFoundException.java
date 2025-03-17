package com.learning.blogappapis.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private int fileValue;

    public ResourceNotFoundException(String resourceName, String fieldName, int fileValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fileValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fileValue = fileValue;
    }

}
