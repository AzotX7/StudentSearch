package com.azot.course.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
public class Material {

    private int id;
    private String title;
    private String content;
    private Date createdAt;
    private String imageURL;

    private User author;

    private List<Comment> comments;

    private List<Category> categories;
}