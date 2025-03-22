package com.learning.blogappapis.repository;

import com.learning.blogappapis.model.Category;
import com.learning.blogappapis.model.Post;
import com.learning.blogappapis.model.User;
import com.learning.blogappapis.payloads.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    Page<Post> findPostByCategoryId(Pageable pageable,Category category);
    Page<Post> findAllByUserId(Pageable pageable, User user);
    List<Post> findByTitleContainingIgnoreCase(String title);
}
