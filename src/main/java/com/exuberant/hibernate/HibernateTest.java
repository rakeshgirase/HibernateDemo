package com.exuberant.hibernate;

public class HibernateTest {

	public static void main(String[] args) {
		CrudService crudService = new CrudService();
		crudService.save();
		crudService.fetchWithHql();
		crudService.fetchWithId();
	}

}