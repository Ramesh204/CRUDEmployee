package com.training.crudemployee.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;

import com.training.crudemployee.entity.Employee;
import com.training.crudemployee.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/rest/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping(value="/all")
	public List<Employee> getAllEmployees(){
		System.out.println("Get all Employees");
		List<Employee> empList = new ArrayList<>();
		
		Iterable<Employee> results = this.employeeRepository.findAll();
		results.forEach(emp->{empList.add(emp);});
		
		return empList;
			
	}
	
	@GetMapping(value="/{id}")
	public Optional<Employee> getEmployeeById(@PathVariable("id") long id) {
		
		System.out.println("Get Employee With ID"+id);
		return this.employeeRepository.findById(id);
	}
	
	
	@PostMapping(value="/add")
	public Employee addEmployee(@RequestBody Employee employee)
	{	
		System.out.println("addEmployee");
		Employee emp = employeeRepository.save(employee);
		return  emp;
		 
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
		 System.out.println("Delete Employee with ID="+id);
		 employeeRepository.deleteById(id);
		 return new ResponseEntity<>("Employee has been deleted!",HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/all")
	public ResponseEntity<String> deleteAllEmployee() {
		 System.out.println("Delete All Employees");
		 employeeRepository.deleteAll();
		 return new ResponseEntity<>("All Employees have been deleted!",HttpStatus.OK);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id,@RequestBody Employee employee){
		System.out.println("update Employee with ID="+id);
		Optional<Employee> employeeData = employeeRepository.findById(id);
		 if(employeeData.isPresent()) {
			 Employee emp = employeeData.get();
			 emp.setEmailAddress(employee.getEmailAddress());
			 emp.setPhoneNumber(employee.getPhoneNumber());
			 return new ResponseEntity<>(employeeRepository.save(emp),HttpStatus.OK);
		 }else
		 {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	
	
}
