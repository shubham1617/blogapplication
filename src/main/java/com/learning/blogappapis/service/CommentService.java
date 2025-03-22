package com.learning.blogappapis.service;


import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.CommentDTO;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, int postId);
    //CommentDTO updateComment(CommentDTO commentDTO, int commentiId);
    void deleteComment(int commentId);
}
