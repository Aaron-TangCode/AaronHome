package com.aaron.springbootcrud.controller;

import com.aaron.springbootcrud.dao.EmployeeDao;
import com.aaron.springbootcrud.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeDao employeeDao;


    //查询所有员工列表页面
    @GetMapping(value = "/emps")
    public String list(Model model){
        Collection<Employee> employees =  employeeDao.getAll();
        model.addAttribute("emps",employees);
        return "emp/list";
    }
}
