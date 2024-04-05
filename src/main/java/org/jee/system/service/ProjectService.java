package org.jee.system.service;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.jee.system.dao.ProjectDAO;
import org.jee.system.dao.jpa.ProjectDAOJPA;
import org.jee.system.model.EmployeeProject;
import org.jee.system.model.Project;

import java.util.List;

public class ProjectService {
    ProjectDAO projectDAO;

    public ProjectService(){
        this.projectDAO = new ProjectDAOJPA();
    }

    public int addProjectService(Project project){
        int result = projectDAO.addProject(project);
        if (result > 0) {
            addSuccessMessage("Project added successfully");
        } else {
            addErrorMessage("Error adding project");
        }
        return result;
    }
    public List<Project> findAllService(){
        return projectDAO.findAll();
    }
    public int findProjectIdByNameService(String name){
        return projectDAO.findProjectIdByName(name);
    }
    public Project findProjectByNameService(String name){
        return projectDAO.findProjectByName(name);
    }
    public Project findProjectByIdService(int id){
        return projectDAO.findProjectById(id);
    }

    public List<EmployeeProject> findAllProjectsEmployeesService(int employeeId){
        return projectDAO. findProjectsByEmployeeId(employeeId) ;
    }

    /**
     * Adds an error message to the FacesContext.
     *
     * @param message The error message to be added.
     */
    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    /**
     * Adds a success message to the FacesContext.
     *
     * @param message The success message to be added.
     */
    private void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
}
