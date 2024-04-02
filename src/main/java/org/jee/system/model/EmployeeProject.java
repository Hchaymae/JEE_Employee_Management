package org.jee.system.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "employee_project")
@NamedQueries({
    @NamedQuery(name = "EmployeeProject.findAll", query = "SELECT ep FROM EmployeeProject ep"),
    @NamedQuery(name = "EmployeeProject.findByEmployeeId", query = "SELECT ep FROM EmployeeProject ep WHERE ep.employee.id = :employeeId"),
    @NamedQuery(name = "EmployeeProject.findByProjectId", query = "SELECT ep FROM EmployeeProject ep WHERE ep.project.id = :projectId"),
    @NamedQuery(name = "EmployeeProject.findByCharge", query = "SELECT ep FROM EmployeeProject ep WHERE ep.charge = :charge")
})
public class EmployeeProject {

    @EmbeddedId
    private EmployeeProjectId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "charge")
    private double charge;
    
    public EmployeeProject() {
    }

    public EmployeeProject(EmployeeProjectId id, Employee employee, Project project, double charge) {
        this.id = id;
        this.employee = employee;
        this.project = project;
        this.charge = charge;
    }

    public EmployeeProjectId getId() {
        return id;
    }

    public void setId(EmployeeProjectId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getCharge() {
        return (int)charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
}
