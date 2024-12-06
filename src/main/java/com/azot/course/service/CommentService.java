package com.azot.course.service;

import com.azot.course.DAO.CommentDAO;
import com.azot.course.DAO.DAOImpl.CommentDAOImpl;
import com.azot.course.DTO.CommentDTO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.Comment;
import com.azot.course.models.Material;
import com.azot.course.models.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class CommentService {
    private final CommentDAOImpl commentDAO;


    public CommentService(Connection connection)     {
        this.commentDAO = new CommentDAOImpl(connection);
    }

    @SneakyThrows
    public void addComment(int materialId,CommentDTO commentDTO){
        Comment comment = fromDTO(commentDTO,materialId);
        commentDAO.addComment(materialId,comment);
    }

    @SneakyThrows
    public CommentDTO getCommentById(int id) {
        Comment comment = commentDAO.getCommentById(id);
        return toDTO(comment);
    }

    @SneakyThrows
    public List<CommentDTO> getAllComments(){
        return commentDAO.getAllComments().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @SneakyThrows
    public void deleteComment(int id) {
        commentDAO.deleteComment(id);
    }
    @SneakyThrows
    public List<CommentDTO> getCommentsByMaterialId(int materialId){
        return commentDAO.findCommentsByMaterialId(materialId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getAuthor().getUsername(),
                comment.getAuthor().getId()
        );
    }

    private Comment fromDTO(CommentDTO commentDTO,int materialId) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        User user = new User();
        user.setId(commentDTO.getAuthorId());
        user.setUsername(commentDTO.getUsername());
        comment.setAuthor(user);

        Material material = new Material();
        material.setId(materialId);
        comment.setMaterial(material);



        return comment;
    }
}