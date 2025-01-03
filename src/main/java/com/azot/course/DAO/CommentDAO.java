package com.azot.course.DAO;

import com.azot.course.models.Comment;

import java.sql.SQLException;
import java.util.List;

public interface CommentDAO {

    void addComment(int materialId, Comment comment) throws SQLException;

    void deleteComment(int id) throws SQLException;

    List<Comment> findCommentsByMaterialId(int materialId) throws SQLException;
}
