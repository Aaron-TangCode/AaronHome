package com.aaron.springbootcrud.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.aaron.springbootcrud.entities.Department;
import com.aaron.springbootcrud.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class EmployeeDao {

	private static Map<Integer, Employee> employees = null;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	static{
		employees = new HashMap<Integer, Employee>();

		employees.put(1001, new Employee(1001, "E-AA", "aa@163.com", 1, new Department(101, "D-AA")));
		employees.put(1002, new Employee(1002, "E-BB", "bb@163.com", 1, new Department(102, "D-BB")));
		employees.put(1003, new Employee(1003, "E-CC", "cc@163.com", 0, new Department(103, "D-CC")));
		employees.put(1004, new Employee(1004, "E-DD", "dd@163.com", 0, new Department(104, "D-DD")));
		employees.put(1005, new Employee(1005, "E-EE", "ee@163.com", 1, new Department(105, "D-EE")));
	}
	
	private static Integer initId = 1006;
	//保存员工
	public void save(Employee employee){
		//没带员工id，就自动生成id
		if(employee.getId() == null){
			employee.setId(initId++);
		}
		//有带员工id，就用员工id
		employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
		employees.put(employee.getId(), employee);
	}
	//查询所有员工
	public Collection<Employee> getAll(){
		return employees.values();
	}
	//通过员工id查询员工
	public Employee get(Integer id){
		return employees.get(id);
	}
	//通过员工Id删除员工
	public void delete(Integer id){
		employees.remove(id);
	}
}
