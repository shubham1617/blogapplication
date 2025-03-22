package com.learning.blogappapis.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;
    private String title;
    private String content;
    private String image;
    private String dateTime;

    @ManyToOne
    @JoinColumn(name = "caetgory_id")
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
