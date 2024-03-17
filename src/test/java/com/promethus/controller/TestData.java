package com.promethus.controller;

import java.util.Arrays;
import java.util.List;

import com.promethus.model.Employee;

public class TestData {
	
	public static List<Employee> getAllEmployees(){
		return Arrays.asList(new Employee(1L, "Tushar Limbhore", "Consultant"),
				new Employee(1L, "Milind Patil", "Sr Consultant"), new Employee(1L, "Shailesh P", "Manager"));
	}
	
	public static Employee getEmployee() {
		return new Employee(1L, "Tushar Limbhore", "Consultant");
	}
	
	public static Employee getUpdatedProduct1() {
		return new Employee(1L, "Tushar Limbhore", "Manager");
	}
	

}
