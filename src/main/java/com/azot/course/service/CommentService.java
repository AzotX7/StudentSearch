package com.azot.course.service;

import com.azot.course.DAO.CommentDAO;
import com.azot.course.entity.Comment;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;

public class CommentService {
    private final CommentDAO commentDAO;


    public CommentService(Connection connection) {
        this.commentDAO = new CommentDAO(connection);
    }

    @SneakyThrows
    public void addComment(int materialId,Comment comment){
        commentDAO.addComment(materialId,comment);
    }

    @SneakyThrows
    public Comment getCommentById(int id) {
        return commentDAO.getCommentById(id);
    }

    @SneakyThrows
    public List<Comment> getAllComments(){
        return commentDAO.getAllComments();
    }

    @SneakyThrows
    public void updateComment(Comment comment) {
        commentDAO.updateComment(comment);
    }

    @SneakyThrows
    public void deleteComment(int id) {
        commentDAO.deleteComment(id);
    }
    @SneakyThrows
    public List<Comment> getCommentsByMaterialId(int materialId){
        return commentDAO.findCommentsByMaterialId(materialId);
    }
}
