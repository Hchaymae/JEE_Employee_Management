package org.jee.system.dao.jpa;

import jakarta.persistence.*;
import org.jee.system.dao.EmployeeDAO;
import org.jee.system.dao.ProjectDAO;
import org.jee.system.model.*;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class EmployeeDAOJPA implements EmployeeDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityTransaction transaction ;

    @PersistenceContext(unitName = "Eclipselink")
    private EntityManager entityManager;

    private ProjectDAO projectDAO;


    public EmployeeDAOJPA() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Eclipselink");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        projectDAO = new ProjectDAOJPA();
    }


    @Override
    public int addEmployee(Employee employee) {
        try {
            transaction.begin();
            if(employee == null) {
                return 0;
            } else {
                entityManager.persist(employee);
                transaction.commit();
                return 1;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Employee> findAll() {
        return entityManager.createNativeQuery("SELECT * FROM employee", Employee.class).getResultList();
    }

    @Override
    public List<Employee> findAllEmployeesSkillsProjects() {
        List<Employee> employees = entityManager.createNativeQuery("SELECT * FROM employee", Employee.class).getResultList();

        for (Employee employee : employees) {
            int employeeId = employee.getId();

            // Get skills
            List<EmployeeSkill> skills = entityManager.createNativeQuery("SELECT s.* FROM employee_skills s WHERE s.employee_id = ?", EmployeeSkill.class)
                    .setParameter(1, employeeId)
                    .getResultList();
            employee.setSkills(skills);

            List<EmployeeProject> projects = entityManager.createNativeQuery("SELECT ep.* FROM employee_project ep WHERE ep.employee_id = ?", EmployeeProject.class)
                    .setParameter(1, employeeId)
                    .getResultList();

            for (EmployeeProject project : projects) {
                Project p = entityManager.find(Project.class, project.getId().getProjectId());
                project.setProject(p);
            }

            employee.setEmployeeProjects(projects);
        }

        return employees;
    }

    @Override
    public int affecterproject(int employeeId, int projectId, double chargePercentage) {
        try {
            transaction.begin();
            EmployeeProject existingEmployeeProject = entityManager.createQuery(
                            "SELECT ep FROM EmployeeProject ep WHERE ep.employee.id = :employeeId AND ep.project.id = :projectId", EmployeeProject.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("projectId", projectId)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);

            if(existingEmployeeProject != null) {
                existingEmployeeProject.setCharge(chargePercentage);
                entityManager.merge(existingEmployeeProject);
            } else {
                Employee employee = entityManager.find(Employee.class, employeeId);
                Project project = entityManager.find(Project.class, projectId);

                EmployeeProject employeeProject = new EmployeeProject();
                employeeProject.setEmployee(employee);
                employeeProject.setProject(project);
                employeeProject.setCharge(chargePercentage);

                entityManager.persist(employeeProject);
            }

            transaction.commit();

            return 1;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return 0;
        }
    }

    public List<EmployeeSkill> findSkillsByEmployeeId(int employeeId) {
        Query query = entityManager.createQuery("SELECT s FROM EmployeeSkill s WHERE s.employee.id = :employeeId");
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    public int deleteSkill(int skillId) {
        try {
            transaction.begin();

            Query query = entityManager.createNativeQuery("DELETE FROM employee_skills WHERE id = ?");
            query.setParameter(1, skillId);
            int result = query.executeUpdate();

            transaction.commit();

            return result;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public int deleteProject(EmployeeProjectId id) {
        try {
            transaction.begin();
            double employeeId = id.getEmployeeId();  // Convert EmployeeProjectId to double (assuming getEmployeeId() returns a double)

            Query query = entityManager.createNativeQuery("DELETE FROM employee_project WHERE employee_id = ?");
            query.setParameter(1, employeeId);
            int result = query.executeUpdate();

            transaction.commit();

            return result;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public int deleteEmployee(int employeeId) {
        try {
            transaction.begin();

            // Delete from employee_project first
            Query deleteEmployeeProjectQuery = entityManager.createNativeQuery("DELETE FROM employee_project WHERE EMPLOYEE_ID = ?");
            deleteEmployeeProjectQuery.setParameter(1, employeeId);
            deleteEmployeeProjectQuery.executeUpdate();

            // Then delete from employee
            Query deleteEmployeeQuery = entityManager.createNativeQuery("DELETE FROM employee WHERE id = ?");
            deleteEmployeeQuery.setParameter(1, employeeId);
            int result = deleteEmployeeQuery.executeUpdate();

            transaction.commit();

            return result;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }




    @Override
    public int findEmployeeIdByName(String name) {
        try {
            Object result = entityManager.createNativeQuery("SELECT id FROM employee WHERE name = ?")
                    .setParameter(1, name)
                    .getSingleResult();
            return ((Number) result).intValue();
        } catch (NoResultException e) {
            return -1;
        }
    }

    @Override
    public Employee findEmployeeById(int id) {
        return entityManager.find(Employee.class, id);
    }
}
