package com.learning.blogappapis.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private List<PostDTO> response;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totatlPages;
    private boolean lastPage;

}
