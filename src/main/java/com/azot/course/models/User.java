package com.azot.course.models;

import com.azot.course.user.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {

    private int id;

    private String username;

    private String email;

    private String password;

    private String salt;

    private Date registrationDate;

    private Role role;

    private List<Material> materials;

    private List<Comment> comments;
}