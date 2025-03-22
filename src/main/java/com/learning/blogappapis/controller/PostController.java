package com.learning.blogappapis.controller;

import com.learning.blogappapis.model.Post;
import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.PostDTO;
import com.learning.blogappapis.payloads.PostResponse;
import com.learning.blogappapis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired private PostService postService;

    @PostMapping("/userId/{id}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable int id,
                                                @PathVariable int categoryId)
    {
        PostDTO post = postService.createPost(postDTO, id, categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable int id, @RequestBody PostDTO postDTO){
        PostDTO data = this.postService.updatePost(postDTO, id);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getAllPostByCategory(@PathVariable int categoryId,
                                                             @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                             @RequestParam(value="pageSize", defaultValue = "5", required = false) int pageSize,
                                                             @RequestParam(value = "sortBy",required = false, defaultValue = "postId") String sortBy,
                                                             @RequestParam(value = "sortDir",required = false, defaultValue = "asc") String sortDir)
    {
        PostResponse postByCategoryId = this.postService.getPostByCategoryId(categoryId, pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postByCategoryId, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getAllPostByUser(@PathVariable int userId,
                                                          @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                          @RequestParam(value="pageSize", defaultValue = "5", required = false) int pageSize,
                                                         @RequestParam(value = "sortBy",required = false, defaultValue = "postId") String sortBy,
                                                          @RequestParam(value = "sortDir",required = false, defaultValue = "asc") String sortDir)
    {
        PostResponse postByUserId  = this.postService.getPostByUserId(userId,pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postByUserId,HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int postId)
    {
        PostDTO postById = this.postService.getPostById(postId);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize",required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(value = "sortBy",required = false, defaultValue = "postId") String sortBy,
            @RequestParam(value = "sortDir",required = false, defaultValue = "asc") String sortDir
    ){
        PostResponse allPost = this.postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allPost,HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId){
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.postService.deletePostById(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Successfully",true,currentDateTime), HttpStatus.OK);
    }

    @GetMapping("/post/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchInPost(@PathVariable String keyword)
    {
        List<PostDTO> postDTOS = this.postService.serachPost(keyword);
        return new ResponseEntity<>(postDTOS,HttpStatus.FOUND);
    }
}
