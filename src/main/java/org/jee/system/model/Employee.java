package org.jee.system.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="employee")
public class Employee {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeProject> employeeProjects;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employee_id")
    private List<EmployeeSkill> skills;

    @Enumerated(EnumType.STRING)
    @Column(name = "post")
    private Post post;

        public Employee() {
                super();
        }

        public Employee(int id, String name, String email, List<EmployeeProject> employeeProjects, List<EmployeeSkill> skills, Post post) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.employeeProjects = employeeProjects;
                this.skills = skills;
                this.post = post;
        }

        public Employee(int id, String name, String email, List<EmployeeSkill> skills, Post post) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.skills = skills;
                this.post = post;
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

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public List<EmployeeProject> getEmployeeProjects() {
                return employeeProjects;
        }

        public void setEmployeeProjects(List<EmployeeProject> employeeProjects) {
                this.employeeProjects = employeeProjects;
        }

        public List<EmployeeSkill> getSkills() {
                return skills;
        }

        public void setSkills(List<EmployeeSkill> skills) {
                this.skills = skills;
        }

        public Post getPost() {
                return post;
        }

        public void setPost(Post post) {
                this.post = post;
        }

        @Override
        public String toString() {
                return "Employee{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", email='" + email + '\'' +
                        ", employeeProjects=" + employeeProjects +
                        ", skills=" + skills +
                        ", post=" + post +
                        '}';
        }
}
