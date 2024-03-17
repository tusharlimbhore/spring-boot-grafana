package com.promethus.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promethus.exception.EmployeeNotFoundException;
import com.promethus.model.Employee;
import com.promethus.repo.EmployeeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Employee API", description = "Employee RESt API")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	private EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Operation(summary = "Get all Employee", tags = { "Employee", "get" })
	@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") })
	@ApiResponse(responseCode = "204", description = "There are no Employees", content = @Content(schema = @Schema()))

	@ApiResponse(responseCode = "500", content = @Content(schema = @Schema()))
	@GetMapping
	public ResponseEntity<java.util.List<Employee>> getAllEmployee() {
		return ResponseEntity.ok(employeeRepository.findAll());
	}

	@Operation(summary = "Find Employee by ID", tags = { "Employee", "get" })
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json"))
	@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) })
	@ApiResponse(responseCode = "500", content = @Content(schema = @Schema()))
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeByID(@PathVariable Long id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			employeeRepository.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(optionalEmployee.get());
		} else {
			throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
		}
	}

	@Operation(summary = "Create new Employee", tags = { "Employee", "post" })
	@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json"))
	@ApiResponse(responseCode = "500", content = @Content(schema = @Schema()))
	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.save(employee));

	}

	@Operation(description = "Update a Employee by ID", tags = { "Employee", "put" })
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json"))
	@ApiResponse(responseCode = "500", content = @Content(schema = @Schema()))
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema()))

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			Employee existingEmployee = optionalEmployee.get();
			existingEmployee.setName(employee.getName());
			existingEmployee.setDesignation(employee.getDesignation());
			return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.save(existingEmployee));

		} else {
			throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
		}

	}

	@Operation(summary = "Delete Employee by ID", tags = { "Employee", "delete" })
	@ApiResponse(responseCode = "204", content = @Content(schema = @Schema()))
	@ApiResponse(responseCode = "500", content = @Content(schema = @Schema))
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			employeeRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
		}
	}
}
