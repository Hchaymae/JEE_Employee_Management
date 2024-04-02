package org.jee.system.dao;

import org.jee.system.model.EmployeeProject;
import org.jee.system.model.Project;

import java.util.List;

public interface ProjectDAO {
    public int addProject(Project project);
    public List<Project> findAll();

    public int findProjectIdByName(String name);
    public Project findProjectByName(String name);
    public Project findProjectById(int id);
    public List<EmployeeProject> findProjectsByEmployeeId(int employeeId);
}
