package org.jee.system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_skills")
@NamedQueries({
    @NamedQuery(name = "EmployeeSkill.findAll", query = "SELECT es FROM EmployeeSkill es"),
    @NamedQuery(name = "EmployeeSkill.findByEmployeeId", query = "SELECT es FROM EmployeeSkill es WHERE es.employee.id = :employeeId"),
    @NamedQuery(name = "EmployeeSkill.findBySkill", query = "SELECT es FROM EmployeeSkill es WHERE es.skill = :skill")
})
public class EmployeeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "skill")
    private String skill;

    public EmployeeSkill() {
        super();
    }

    public EmployeeSkill(Employee employee, String skill) {
        this.employee = employee;
        this.skill = skill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "EmployeeSkill{" +
                ", skill='" + skill + '\'' +
                '}';
    }
}
