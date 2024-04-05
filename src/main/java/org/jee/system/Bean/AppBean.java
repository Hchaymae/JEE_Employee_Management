package org.jee.system.Bean;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.component.html.HtmlCommandButton;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import org.jee.system.model.*;
import org.jee.system.service.EmployeeService;
import org.jee.system.service.ProjectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name="appBean")
@ViewScoped
public class AppBean {
    private List<Employee> employees;

    @Inject
    private List<Employee> employeesList;
    private EmployeeService employeeService;
    private Employee employee;
    private HtmlCommandButton affecterButton;
    private List<SelectItem> posts;
    private List<Project> projects;
    private ProjectService projectService;
    private Project project;

    private String skillsString;
    private String selectedEmployee;
    private String selectedProject;
    private String selectedPercentage;
    private EmployeeSkill skill;

    private List<String> employeeSkills = new ArrayList<>();


    public AppBean() {
        employeeService = new EmployeeService();

        employees = employeeService.findAllService();
        employee = new Employee();
        affecterButton = new HtmlCommandButton();

        projectService = new ProjectService();
        projects = projectService.findAllService();
        project = new Project();
        posts = Arrays.stream(Post.values()).map(p -> new SelectItem(p, p.getLabel())).collect(Collectors.toList());
        skill = new EmployeeSkill();
        employeesList = employeeService.findAllEmployeesSkillsProjectsService();
    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public HtmlCommandButton getAffecterButton() {
        return affecterButton;
    }

    public void setAffecterButton(HtmlCommandButton affecterButton) {
        this.affecterButton = affecterButton;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(String selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public String getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }

    public String getSelectedPercentage() {
        return selectedPercentage;
    }

    public void setSelectedPercentage(String selectedPercentage) {
        this.selectedPercentage = selectedPercentage;
    }

    public List<SelectItem> getPosts() {
        return posts;
    }

    public void setPosts(List<SelectItem> posts) {
        this.posts = posts;
    }

    public List<String> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(List<String> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public String getSkillsString() {
        return skillsString;
    }

    public void setSkillsString(String skillsString) {
        this.skillsString = skillsString;
    }

    //    *****************************

    public void submit() {
        System.out.println("Selected Employee: " + selectedEmployee);
        System.out.println("Selected Project: " + selectedProject);
        System.out.println("Selected Percentage: " + selectedPercentage);
        double percentage = Double.parseDouble(selectedPercentage);

        int employeeId = employeeService.findEmployeeIdByNameService(selectedEmployee);
        int projectId = projectService.findProjectIdByNameService(selectedProject);

        employeeService.affecterprojectService(employeeId, projectId, percentage);
        loadEmployeesList();
    }

    private void loadEmployeesList() {
        employees = employeeService.findAllService();
        employeesList = employeeService.findAllEmployeesSkillsProjectsService();
    }

    public void delete(int employeeId) {
        try {
            List<EmployeeSkill> skills = employeeService.findSkillsEmployeeById(employeeId);
            for (EmployeeSkill skill : skills) {
                employeeService.deleteSkillService(skill.getId());
            }

            System.out.println(projects);
            List<EmployeeProject> projects = projectService.findAllProjectsEmployeesService(employeeId);
            for (EmployeeProject project : projects) {
                employeeService.deleteProjectService(project.getId());
            }

            employeeService.deleteEmployeeService(employeeId);

            loadEmployeesList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addEmployee() {
        List<EmployeeSkill> skills = convertToEmployeeSkills(Arrays.asList(skillsString.split("\\s*,\\s*")));

        employee.setSkills(skills);

        if(employeeService.addEmployeeService(employee) > 0){
            this.employee = new Employee();
            this.skill = new EmployeeSkill();
            loadEmployeesList();
            return "list?faces-redirect=true";
        } else {
            return null;
        }
    }

    private List<EmployeeSkill> convertToEmployeeSkills(List<String> skills) {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        for (String skillName : skills) {
            EmployeeSkill employeeSkill = new EmployeeSkill();
            employeeSkill.setSkill(skillName);
            employeeSkills.add(employeeSkill);
        }
        return employeeSkills;
    }
    public void addProject() {
        if(projectService.addProjectService(project) > 0){
            this.project = new Project();
            loadProjectsList();
        }
    }

    private void loadProjectsList() {
        projects = projectService.findAllService();
    }

}
