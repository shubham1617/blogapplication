package com.learning.blogappapis.service;


import com.learning.blogappapis.payloads.CommentDTO;

public interface CommentService
{

    CommentDTO createComment(CommentDTO commentDTO, int postId);
    //CommentDTO updateComment(CommentDTO commentDTO, int commentiId);
    void deleteComment(int commentId);
}
