package com.azot.course.service;

import com.azot.course.DTO.CommentDTO;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {

    void addComment(int materialId, CommentDTO commentDTO) throws SQLException;

    void deleteComment(int id) throws SQLException;

    List<CommentDTO> getCommentsByMaterialId(int materialId) throws SQLException;


}
