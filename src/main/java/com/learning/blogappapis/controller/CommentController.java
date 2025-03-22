package com.learning.blogappapis.controller;

import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.CommentDTO;
import com.learning.blogappapis.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired private CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable int postId){
        CommentDTO comment = this.commentService.createComment(commentDTO, postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Deleted Successfully!!!",true, LocalDateTime.now()),HttpStatus.OK);
    }

}
