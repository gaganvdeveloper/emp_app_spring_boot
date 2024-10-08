package org.jsp.emp.service;

import java.util.List;
import java.util.Optional;

import org.jsp.emp.dao.EmployeeDao;
import org.jsp.emp.entity.Employee;
import org.jsp.emp.exceptionclasses.InvalidCredentialsException;
import org.jsp.emp.exceptionclasses.InvalidEmployeeIdException;
import org.jsp.emp.exceptionclasses.NoActiveEmployeeFoundException;
import org.jsp.emp.exceptionclasses.NoEmployeeFoundException;
import org.jsp.emp.responsestructure.ResponseStructure;
import org.jsp.emp.util.EmployeeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
	@Autowired
	private EmployeeDao dao;

	public ResponseEntity<?> saveEmployee(Employee employee) {
		employee.setStatus(EmployeeStatus.IN_ACTIVE);
		return ResponseEntity.status(HttpStatus.CREATED).body( ResponseStructure.builder().status(HttpStatus.CREATED.value()).message("Employee Saved Successfully...").body(dao.saveEmployee(employee)).build());
	}

	public ResponseEntity<?> updateEmployee(Employee employee) {
		return ResponseEntity.status(HttpStatus.OK).body( ResponseStructure.builder().status(HttpStatus.OK.value()).message("Employee Updated Successfully...").body(dao.updateEmployee(employee)).build());
	}

	public ResponseStructure<Employee> findEmployeeById(int id) {
		Optional<Employee> optional = dao.findEmployeeById(id);
		ResponseStructure<Employee> structure = new ResponseStructure<>();
		if (optional.isEmpty())
			throw InvalidEmployeeIdException.builder().message("Invalid Employee Id... Unable to find Employee...").build();
		Employee employee = optional.get();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Employee Found Successfully...");
		structure.setBody(employee);
		return structure;
	}

	public ResponseStructure<List<Employee>> findAllEmployee() {
		List<Employee> employees = dao.findAllActiveEmployees();
		ResponseStructure<List<Employee>> structure = new ResponseStructure<>();
		if (employees.isEmpty()) {
			throw new NoActiveEmployeeFoundException("No Active Employee Present in the Database Table...");
		}
//		ArrayList<Employee> activeEmployees = new ArrayList<>();
//		for(Employee e : employees) {
//			if(e.getStatus()==EmployeeStatus.ACTIVE)
//				activeEmployees.add(e);
//		}
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("All Employees Found Successfully...");
		structure.setBody(employees);
		return structure;
	}

	public ResponseStructure<String> deleteEmployeeById(int id) {
		Optional<Employee> optional = dao.findEmployeeById(id);
		ResponseStructure<String> structure = new ResponseStructure<>();
		if (optional.isEmpty())
			throw new InvalidEmployeeIdException("Invalid Employee Id Unable to delete");
		dao.deleteEmployeeById(id);
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Emplopyee deleted Successfully...");
		structure.setBody("Employe Deleted...");
		return structure;
	}

	public ResponseStructure<Employee> findEmployeeByEmailAndPassword(String email, String password) {
		ResponseStructure<Employee> structure = new ResponseStructure<>();
		Optional<Employee> optional = dao.findEmployeeByEmailAndPassword(email, password);
		if (optional.isEmpty())
			throw new InvalidCredentialsException("Invalid Email or Password...");
		Employee employee = optional.get();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Verification Successfull...");
		structure.setBody(employee);
		return structure;
	}

	public ResponseStructure<List<Employee>> findEmployeeByname(String name) {
		List<Employee> employees = dao.findEmployeeByName(name);
		ResponseStructure<List<Employee>> structure = new ResponseStructure<>();
		if (employees.isEmpty())
			throw new NoEmployeeFoundException("No Matching Employees Found for the Requested Name");
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Employess Found Successfully...");
		structure.setBody(employees);
		return structure;
	}

	public ResponseStructure<Employee> setEmployeeStatusToActive(int id) {
		Optional<Employee> optional = dao.findEmployeeById(id);
		ResponseStructure<Employee> structure = new ResponseStructure<>();
		if (optional.isEmpty())
			throw new InvalidEmployeeIdException("Invalid Employee Id Unable to Set Status to ACTIVE");
		Employee employee = optional.get();
		employee.setStatus(EmployeeStatus.ACTIVE);
		employee = dao.updateEmployee(employee);
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Employee Status Updated to ACTIVE Successfully...");
		structure.setBody(employee);
		return structure;
	}

	public ResponseStructure<Employee> setEmployeeStatusToInActive(int id) {
		Optional<Employee> optional = dao.findEmployeeById(id);
		ResponseStructure<Employee> structure = new ResponseStructure<>();
		if (optional.isEmpty())
			throw new InvalidEmployeeIdException("Invalid Employee Id Unable to set Status to IN_ACTIVE");
		Employee employee = optional.get();
		employee.setStatus(EmployeeStatus.IN_ACTIVE);
		employee = dao.updateEmployee(employee);
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Employee Status Updated to IN_ACTIVE Successfully...");
		structure.setBody(employee);
		return structure;
	}

}
