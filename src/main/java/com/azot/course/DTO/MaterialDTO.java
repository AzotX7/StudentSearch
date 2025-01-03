package com.azot.course.DTO;

import com.azot.course.models.Category;
import com.azot.course.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {

    private int id;
    private String title;
    private String content;
    private Date createdAt;
    private int photoId;
    private int authorId;
    private List<Category> categories;

}
