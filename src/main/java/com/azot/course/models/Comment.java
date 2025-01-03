package com.azot.course.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Comment {
    private int id;

    private String text;

    private Date createdAt;

    private User author;

    private Material material;
}