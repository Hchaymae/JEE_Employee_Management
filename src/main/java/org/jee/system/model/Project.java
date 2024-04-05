package org.jee.system.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="project")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name"),
    @NamedQuery(name = "Project.findByBudget", query = "SELECT p FROM Project p WHERE p.budget = :budget")
})
public class Project {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "budget")
    private double budget;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeProject> employeeProjects;

    public Project() {
        super();
    }

    public Project(int id, String name, double budget, List<EmployeeProject> employeeProjects) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.employeeProjects = employeeProjects;
    }

    public Project(int id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }

    public void setEmployeeProjects(List<EmployeeProject> employeeProjects) {
        this.employeeProjects = employeeProjects;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budget=" + budget +
                ", employeeProjects=" + employeeProjects +
                '}';
    }
      @PostPersist
    private void insertProject() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YourPersistenceUnitName");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        
        em.createNativeQuery("INSERT INTO project (name, budget) VALUES ('CTM', 10000)").executeUpdate();
        em.createNativeQuery("INSERT INTO project (name, budget) VALUES ('Nestle', 200000)").executeUpdate();
        em.createNativeQuery("INSERT INTO project (name, budget) VALUES ('Adidas', 155000)").executeUpdate();
        em.createNativeQuery("INSERT INTO project (name, budget) VALUES ('Stell', 255400)").executeUpdate();
        em.createNativeQuery("INSERT INTO project (name, budget) VALUES ('Energie', 10000)").executeUpdate();

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
