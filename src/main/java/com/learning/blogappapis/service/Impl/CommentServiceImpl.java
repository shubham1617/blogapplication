package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.exceptions.ResourceNotFoundException;
import com.learning.blogappapis.model.Comment;
import com.learning.blogappapis.model.Post;
import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.CommentDTO;
import com.learning.blogappapis.repository.CommentRepo;
import com.learning.blogappapis.repository.PostRepo;
import com.learning.blogappapis.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired  private PostRepo postRepo;
    @Autowired private CommentRepo commentRepo;
    @Autowired private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment mapToClass = modelMapper.map(commentDTO, Comment.class);
        mapToClass.setPost(post);
        Comment save = this.commentRepo.save(mapToClass);
        CommentDTO classToDTO = modelMapper.map(save, CommentDTO.class);
        return classToDTO;
    }

    @Override
    public  void deleteComment(int commentId) {
        LocalDateTime time = LocalDateTime.now();
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        this.commentRepo.delete(comment);
    }
}
