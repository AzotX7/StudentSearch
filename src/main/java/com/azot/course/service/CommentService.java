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
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CommentService {
    private final CommentDAO commentDAO;


    public CommentService(Connection connection)     {
        this.commentDAO = new CommentDAOImpl(connection);
    }


    public void addComment(int materialId,CommentDTO commentDTO) throws SQLException {
        Comment comment = fromDTO(commentDTO,materialId);
        commentDAO.addComment(materialId,comment);
    }


    public void deleteComment(int id) throws SQLException {
        commentDAO.deleteComment(id);
    }


    public List<CommentDTO> getCommentsByMaterialId(int materialId) throws SQLException{
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