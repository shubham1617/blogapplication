package com.learning.blogappapis.repository;

import com.learning.blogappapis.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
