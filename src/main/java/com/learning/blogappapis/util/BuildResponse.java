package com.learning.blogappapis.util;


import com.learning.blogappapis.model.Post;
import com.learning.blogappapis.payloads.PostDTO;
import com.learning.blogappapis.payloads.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildResponse {

    @Autowired private ModelMapper modelMapper;


    public PostResponse buildResponseFromDBResult(Page<Post> posts){
        List<Post> result = posts.getContent();
        /*List<PostDTO> postList = new ArrayList<>();
        for (Post post : result) {
            postList.add(modelMapper.map(post, PostDTO.class));
        }*/
        List<PostDTO> postList = result.stream()
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        PostResponse response = new PostResponse();
        response.setResponse(postList);
        response.setTotalElement(posts.getTotalElements());
        response.setPageNumber(posts.getNumber());
        response.setLastPage(posts.isLast());
        response.setTotatlPages(posts.getTotalPages());
        return response;

    }

}
