package com.learning.blogappapis.controller;

import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.PostDTO;
import com.learning.blogappapis.payloads.PostResponse;
import com.learning.blogappapis.service.FileUploadService;
import com.learning.blogappapis.service.PostService;
import com.learning.blogappapis.util.Constant;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired private PostService postService;
    @Autowired private FileUploadService fileUploadService;
    @Value("${filepath}") private String path;

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
                                                             @RequestParam(value = Constant.PAGE_NO, defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                             @RequestParam(value=Constant.PAGE_SIZE, defaultValue = Constant.DEFAULT_PAGE_VALUE, required = false) int pageSize,
                                                             @RequestParam(value = Constant.SORT_BY, defaultValue = Constant.POST_ID,required = false) String sortBy,
                                                             @RequestParam(value = Constant.SORT_DIR,defaultValue = Constant.ACCENDING,required = false) String sortDir)
    {
        PostResponse postByCategoryId = this.postService.getPostByCategoryId(categoryId, pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postByCategoryId, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getAllPostByUser(@PathVariable int userId,
                                                          @RequestParam(value = Constant.PAGE_NO, defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                          @RequestParam(value=Constant.PAGE_SIZE, defaultValue = Constant.DEFAULT_PAGE_VALUE, required = false) int pageSize,
                                                          @RequestParam(value = Constant.SORT_BY, defaultValue = Constant.POST_ID,required = false) String sortBy,
                                                          @RequestParam(value = Constant.SORT_DIR, defaultValue = Constant.ACCENDING,required = false) String sortDir)
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
            @RequestParam(value = Constant.PAGE_NO, defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value=Constant.PAGE_SIZE, defaultValue = Constant.DEFAULT_PAGE_VALUE, required = false) int pageSize,
            @RequestParam(value = Constant.SORT_BY, defaultValue = Constant.POST_ID,required = false) String sortBy,
            @RequestParam(value = Constant.SORT_DIR, defaultValue = Constant.ACCENDING,required = false) String sortDir)
    {
        PostResponse allPost = this.postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allPost,HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable int postId)
    {
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




    @PostMapping("/post/{postId}/image/upload")
        public ResponseEntity<PostDTO> uploadFile(@PathVariable int postId, @RequestParam("imageName") MultipartFile file) throws IOException
    {
        String response = null;
            PostDTO postById = this.postService.getPostById(postId);
                response = this.fileUploadService.uploadFile(path, file);
                postById.setImage(response);
                PostDTO postDTO = this.postService.updatePost(postById, postId);
                return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}/getImage/",produces =MediaType.IMAGE_JPEG_VALUE )
    public void downloadAndServeFile(@PathVariable int postId, HttpServletResponse response) throws IOException
    {
        PostDTO postById = this.postService.getPostById(postId);
        String imageId = postById.getImage();
        InputStream resourceAsStream = this.fileUploadService.getResourceAsStream(path, imageId);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourceAsStream,response.getOutputStream());
    }

}
