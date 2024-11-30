package com.azot.course.DAO;

import com.azot.course.entity.Group;
import com.azot.course.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {

    private final Connection connection;


    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    public void addGroup(Group group) throws SQLException {
        String sql = "INSERT INTO Groups (name, description, created_at, admin_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, group.getName());
            stmt.setString(2, group.getDescription());

            stmt.setTimestamp(3, new Timestamp(group.getCreatedAt().getTime()));

            stmt.setInt(4, group.getOwner().getId());

            stmt.executeUpdate();
        }
    }

    public Group getGroupById(int id) throws SQLException {
        String sql = "SELECT * FROM Groups WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Group group = new Group();
                    group.setId(rs.getInt("id"));
                    group.setName(rs.getString("name"));
                    group.setDescription(rs.getString("description"));

                    group.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                    int ownerId = rs.getInt("admin_id");
                    User owner = new User();
                    owner.setId(ownerId);
                    group.setOwner(owner);

                    return group;
                }
            }
        }
        return null;
    }


    public List<Group> getAllGroups() throws SQLException {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM Groups";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));
                group.setDescription(rs.getString("description"));

                group.setCreatedAt(new Date(rs.getTimestamp("created_at").getTime()));

                int ownerId = rs.getInt("admin_id");
                User owner = new User();
                owner.setId(ownerId);
                group.setOwner(owner);

                groups.add(group);
            }
        }
        return groups;
    }

    public void updateGroup(Group group) throws SQLException {
        String sql = "UPDATE Groups SET name = ?, description = ?, created_at = ?, admin_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, group.getName());
            stmt.setString(2, group.getDescription());

            stmt.setTimestamp(3, new Timestamp(group.getCreatedAt().getTime()));

            stmt.setInt(4, group.getOwner().getId());
            stmt.setInt(5, group.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteGroup(int id) throws SQLException {
        String sql = "DELETE FROM Groups WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
