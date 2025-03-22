package com.learning.blogappapis.payloads;

import com.learning.blogappapis.model.Category;
import com.learning.blogappapis.model.Comment;
import com.learning.blogappapis.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private String postId;
    private String title;
    private String content;
    private String image;
    private String dateTime;
    private CategoryDTO category;
    private UserDTO user;
    private List<Comment> comments = new ArrayList<>();
}
