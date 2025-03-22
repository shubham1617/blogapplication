package com.learning.blogappapis.service;


import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.PostDTO;
import com.learning.blogappapis.payloads.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface PostService {

    PostDTO createPost(PostDTO postDTO, int postId, int categoryId);
    PostDTO updatePost(PostDTO postDTO,int id);
    PostDTO getPostById(int id);
    PostResponse getPostByUserId(int id, int pageNo, int pageSize,String sortBy, String sortDir);
    PostResponse getPostByCategoryId(int id, int pageNo, int pageSize,String sortBy, String sortDir);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    List<PostDTO> serachPost(String title);
    void deletePostById(int id);
}
