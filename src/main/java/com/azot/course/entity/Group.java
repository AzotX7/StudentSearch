package com.azot.course.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Group {

    private int id;
    private String name;
    private String description;
    private Date createdAt;

    private User owner;

    private List<User> members;

    private List<Material> materials;
}
