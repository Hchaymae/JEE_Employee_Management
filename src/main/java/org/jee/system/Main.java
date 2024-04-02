package org.jee.system;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jee.system.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Eclipselink");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();


        // Create Employees
        Employee employee1 = new Employee(1, "John Doe", "john.doe@example.com", null, Post.DEVOPS);
        Employee employee2 = new Employee(2, "Jane Doe", "jane.doe@example.com", null, Post.MANAGER);

        em.persist(employee1);
        em.persist(employee2);

        // Create EmployeeSkills for employee1
        List<EmployeeSkill> employee1Skills = new ArrayList<>();
        EmployeeSkill skill1 = new EmployeeSkill(employee1, "Java");
        EmployeeSkill skill2 = new EmployeeSkill(employee1, "SQL");
        employee1Skills.add(skill1);
        employee1Skills.add(skill2);

        skill1.setEmployee(employee1);
        skill2.setEmployee(employee1);

        em.persist(skill1);
        em.persist(skill2);

        employee1.setSkills(employee1Skills);
        em.merge(employee1);

        // Create EmployeeSkills for employee2
        List<EmployeeSkill> employee2Skills = new ArrayList<>();
        EmployeeSkill skill3 = new EmployeeSkill(employee2, "Java");
        EmployeeSkill skill4 = new EmployeeSkill(employee2, "Python");
        employee2Skills.add(skill3);
        employee2Skills.add(skill4);

        skill3.setEmployee(employee2);
        skill4.setEmployee(employee2);

        em.persist(skill3);
        em.persist(skill4);

        employee2.setSkills(employee2Skills);
        em.merge(employee2);


        // Create Projects
        Project project1 = new Project(1, "Project A", 10000);
        Project project2 = new Project(2, "Project B", 15000);

        em.persist(project1);
        em.persist(project2);

        // Assign Projects to Employees with roles
        EmployeeProject employeeProject1 = new EmployeeProject(new EmployeeProjectId(employee1.getId(), project1.getId()), employee1, project1, 50.0);
        EmployeeProject employeeProject2 = new EmployeeProject(new EmployeeProjectId(employee1.getId(), project2.getId()), employee1, project2, 30.0);
        EmployeeProject employeeProject3 = new EmployeeProject(new EmployeeProjectId(employee2.getId(), project2.getId()), employee2, project2, 20.0);

        em.persist(employeeProject1);
        em.persist(employeeProject2);
        em.persist(employeeProject3);

        em.getTransaction().commit();

        em.close();
        emf.close();

        System.out.println("Initialization completed successfully.");
    }
}
