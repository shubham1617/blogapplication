package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.exceptions.ResourceNotFoundException;
import com.learning.blogappapis.model.Category;
import com.learning.blogappapis.model.Post;
import com.learning.blogappapis.model.User;
import com.learning.blogappapis.payloads.PostDTO;
import com.learning.blogappapis.payloads.PostResponse;
import com.learning.blogappapis.repository.CategoryRepo;
import com.learning.blogappapis.repository.PostRepo;
import com.learning.blogappapis.repository.UserRepo;
import com.learning.blogappapis.service.PostService;
import com.learning.blogappapis.util.BuildResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl  implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private BuildResponse buildResponse;



    @Override
    public PostDTO createPost(PostDTO postDTO, int postId, int categoryId) {
        User user = userRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", postId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", postId));
        Post post = modelMapper.map(postDTO, Post.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        post.setDateTime(formattedDateTime);
        post.setCategoryId(category);
        post.setUserId(user);
        Post save = this.postRepo.save(post);
        PostDTO result = modelMapper.map(save, PostDTO.class);
        return result;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, int id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", id));
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setImage(postDTO.getImage());
        Post updatedPost = this.postRepo.save(post);
        PostDTO result = this.modelMapper.map(updatedPost, PostDTO.class);
        return result;
    }

    @Override
    public PostDTO getPostById(int id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", id));
        PostDTO result = this.modelMapper.map(post, PostDTO.class);
        return result;
    }

    @Override
    public PostResponse getPostByUserId(int id, int pageNo, int pageSize,String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else if (sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", id));
        Pageable pageRequest = PageRequest.of(pageNo, pageSize,sort);
        Page<Post> content = this.postRepo.findAllByUserId(pageRequest, user);
        PostResponse response = buildResponse.buildResponseFromDBResult(content);
        return response;
    }

    @Override
    public PostResponse getPostByCategoryId(int id, int pageNo, int pageSize,String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else if (sortDir.equalsIgnoreCase("desc")){
            sort = Sort.by(sortBy).descending();
        }
        Category category = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));
        Pageable pageRequest = PageRequest.of(pageNo, pageSize,sort);
        Page<Post> content = this.postRepo.findPostByCategoryId(pageRequest, category);
        PostResponse response = buildResponse.buildResponseFromDBResult(content);
        return response;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        else if (sortDir.equalsIgnoreCase("desc"))
        {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = this.postRepo.findAll(pageRequest);
        PostResponse response = buildResponse.buildResponseFromDBResult(all);
        return response;
    }

    @Override
    public List<PostDTO> serachPost(String title) {
        List<Post> byTitleContainingIgnoreCase = this.postRepo.findByTitleContainingIgnoreCase(title);
        List<PostDTO> response = byTitleContainingIgnoreCase.stream().map(result -> modelMapper.map(result, PostDTO.class)).collect(Collectors.toList());
        return response;
    }

    @Override
        public void deletePostById ( int id){
            Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", id));
            this.postRepo.delete(post);
        }

}
