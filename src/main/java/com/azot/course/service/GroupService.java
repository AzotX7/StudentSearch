package com.azot.course.service;

import com.azot.course.DAO.GroupDAO;
import com.azot.course.entity.Group;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.List;

public class GroupService {
    private final GroupDAO groupDAO;


    public GroupService(Connection connection) {
        this.groupDAO = new GroupDAO(connection);
    }

    @SneakyThrows
    public void addGroup(Group group){
        groupDAO.addGroup(group);
    }

    @SneakyThrows
    public Group getGroupById(int id){
        return groupDAO.getGroupById(id);
    }

    @SneakyThrows
    public List<Group> getAllGroups() {
        return groupDAO.getAllGroups();
    }

    @SneakyThrows
    public void updateGroup(Group group) {
        groupDAO.updateGroup(group);
    }

    @SneakyThrows
    public void deleteGroup(int id) {
        groupDAO.deleteGroup(id);
    }
}
