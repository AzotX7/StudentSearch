package com.azot.course.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private int id;
    private String text;
    private Date createdAt;
    private String username;
    private int authorId;
}
