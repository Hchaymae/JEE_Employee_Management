package org.jee.system.dao;

import org.jee.system.model.Employee;
import org.jee.system.model.EmployeeProjectId;
import org.jee.system.model.EmployeeSkill;
import org.jee.system.model.Project;

import java.util.List;

public interface EmployeeDAO {
    public int addEmployee(Employee employee);
    public List<Employee> findAll();
    public int affecterproject(int employeeid, int projectid, double charge);
    public int findEmployeeIdByName(String name);
    public Employee findEmployeeById(int id);
    public List<Employee> findAllEmployeesSkillsProjects();
    public int deleteProject(EmployeeProjectId id);
    public int deleteSkill(int skillId);
    public int deleteEmployee(int employeeId);
   public  List<EmployeeSkill> findSkillsByEmployeeId(int employeeId);
}
