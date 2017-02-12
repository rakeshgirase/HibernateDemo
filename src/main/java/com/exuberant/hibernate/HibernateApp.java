package com.exuberant.hibernate;

import com.exuberant.hibernate.service.CrudService;
import com.exuberant.hibernate.service.EmployeeService;

public class HibernateApp {

	public static void main(String[] args) {
		EmployeeService empService = new EmployeeService();
		crudService.save();
		crudService.fetchWithHql();
		crudService.fetchWithId();
	}

}