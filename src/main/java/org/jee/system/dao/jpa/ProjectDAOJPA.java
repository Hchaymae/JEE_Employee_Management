package org.jee.system.dao.jpa;

import jakarta.persistence.*;
import org.jee.system.dao.ProjectDAO;
import org.jee.system.model.Employee;
import org.jee.system.model.EmployeeProject;
import org.jee.system.model.Project;

import java.util.List;

public class ProjectDAOJPA implements ProjectDAO {

    private EntityManagerFactory entityManagerFactory;
    @PersistenceContext(unitName = "Eclipselink")
    private EntityManager entityManager;


    public ProjectDAOJPA() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }
    @Override
    public int addProject(Project project) {
        if(project == null) {
            return 0;
        }else{
            entityManager.persist(project);
            entityManager.getTransaction().commit();
            return 1;
        }
    }

    @Override
    public List<Project> findAll() {
        return entityManager.createNativeQuery("SELECT * FROM project", Project.class).getResultList();
    }


    public void deleteProject(EmployeeProject project) {
        entityManager.remove(entityManager.merge(project));
    }

    @Override
    public int findProjectIdByName(String name) {
        try {
            Object result = entityManager.createNativeQuery("SELECT id FROM project WHERE name = ?")
                    .setParameter(1, name)
                    .getSingleResult();
            return ((Number) result).intValue();
        } catch (NoResultException e) {
            return -1;
        }
    }

    @Override
    public Project findProjectByName(String name) {
        try {
            return entityManager.createQuery("SELECT p FROM Project p WHERE p.name = :name", Project.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Project findProjectById(int id) {
        return entityManager.find(Project.class, id);
    }


    @Override
    public List<EmployeeProject> findProjectsByEmployeeId(int employeeId) {
        return entityManager.createNativeQuery("SELECT ep.* FROM employee_project ep WHERE ep.employee_id = ?", EmployeeProject.class)
                .setParameter(1, employeeId)
                .getResultList();
    }

}
