package com.promethus.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.promethus.exception.EmployeeNotFoundException;
import com.promethus.model.Employee;
import com.promethus.repo.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	void testGetAllEmployees() {
		List<Employee> products = TestData.getAllEmployees();
		when(employeeRepository.findAll()).thenReturn(products);
		ResponseEntity<List<Employee>> responseEntity = employeeController.getAllEmployee();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		List<Employee> list = responseEntity.getBody();
		assertNotNull(list);
		assertEquals(3, list.size());
	}

	@Test
	void testGetEmployeeByID() {
		Optional<Employee> product = Optional.of(TestData.getEmployee());
		when(employeeRepository.findById(Mockito.anyLong())).thenReturn(product);
		ResponseEntity<Employee> responseEntity = employeeController.getEmployeeByID(1L);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals("Tushar Limbhore", responseEntity.getBody().getName());
		assertNotNull(responseEntity.getBody().getId());
	}

	@Test
	void testEmployeeNotFound() {
		EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
				() -> employeeController.getEmployeeByID(10L));
		assertTrue(exception.getMessage().contains("not found"));
	}

	@Test
	void testSaveEmployee() {
		when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(TestData.getEmployee());
		ResponseEntity<Employee> responseEntity = employeeController.saveEmployee(TestData.getEmployee());
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals("Consultant", responseEntity.getBody().getDesignation());
	}


	@Test
	void testUpdateEmployee() {
		when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(TestData.getEmployee()));
		Employee updatedEmployee = TestData.getEmployee();
		updatedEmployee.setId(9L);
		updatedEmployee.setName("Mayue K");
		when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updatedEmployee);
		ResponseEntity<Employee> responseEntity = employeeController.updateEmployee(1L, updatedEmployee);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertEquals("Mayue K", responseEntity.getBody().getName());
	}


	@Test
	void testEmployeeNotFoundInUpdate() {
		EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
				() -> employeeController.updateEmployee(10L, TestData.getEmployee()));
		assertTrue(exception.getMessage().contains("not found"));
	}

	@Test
	void testDeleteEmployee() {
		when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(TestData.getEmployee()));
		ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(1L);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	
	@Test
	void testDeleteEmployeeNotFound() {
		EmployeeNotFoundException exception = assertThrows(EmployeeNotFoundException.class,
				() -> employeeController.deleteEmployee(10L));
		assertTrue(exception.getMessage().contains("not found"));
	}
}
