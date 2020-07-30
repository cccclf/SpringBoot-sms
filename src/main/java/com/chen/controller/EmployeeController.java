package com.chen.controller;

import com.chen.dao.DepartmentDao;
import com.chen.dao.EmployeeDao;
import com.chen.pojo.Department;
import com.chen.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    @RequestMapping("/emps")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps",employees);
        return "/emps/list";
    }

    //增加员工
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //查处所有部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emps/add";
    }
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        //添加的操作
        employeeDao.save(employee);
        return "redirect:/emps";//重定向：url会发生改变
    }

    //修改员工信息
    @GetMapping("/emp/{id}")    //点击编辑跳转到员工的修改页面
    public String toUpdateEmp(@PathVariable("id")Integer id,
                              Model model){
        //查出原来的数据
        Employee employee = employeeDao.getEmployeeByTd(id);
        //返回给页面
        model.addAttribute("emp",employee);
        //查处所有部门的信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emps/update";
    }
    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee){
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id")Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }

}
