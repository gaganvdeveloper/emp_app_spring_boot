package org.jsp.emp.controller;

import java.util.List;

import org.jsp.emp.entity.Employee;
import org.jsp.emp.responsestructure.ResponseStructure;
import org.jsp.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@PostMapping
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
		return service.saveEmployee(employee);
	}

	@PutMapping
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		return service.updateEmployee(employee);
	}

	@GetMapping
	public ResponseStructure<List<Employee>> findAllEmpolyees() {
		return service.findAllEmployee();
	}
 
	@GetMapping("/{id}")
	public ResponseStructure<Employee> findEmployeeById(@PathVariable int id) {
		return service.findEmployeeById(id);
	}

	@GetMapping("/name/{name}")
	public ResponseStructure<List<Employee>> findEmployeesByName(@PathVariable String name) {
		return service.findEmployeeByname(name);
	}

	@GetMapping("/{email}/{password}")
	public ResponseStructure<Employee> findEmployeeByEmailAndPassword(@PathVariable String email,
			@PathVariable String password) {
		return service.findEmployeeByEmailAndPassword(email, password);
	}

	@DeleteMapping("/{id}")
	public ResponseStructure<String> deleteEmployeeById(@PathVariable int id) {
		return service.deleteEmployeeById(id);
	}

	@PatchMapping("/active/{id}")
	public ResponseStructure<Employee> setEmployeeStatusToActive(@PathVariable int id) {
		return service.setEmployeeStatusToActive(id);
	}

	@PatchMapping("/inactive/{id}")
	public ResponseStructure<Employee> setEmployeeStatusToInActive(@PathVariable int id) {
		return service.setEmployeeStatusToInActive(id);
	}
	
	
	

}
