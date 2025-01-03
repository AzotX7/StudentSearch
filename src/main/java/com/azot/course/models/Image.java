package com.azot.course.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Image {

    private int id;
    private String fileName;
    private byte[] fileData;
    private String contentType;
}

