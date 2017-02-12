package com.exuberant.hibernate.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.exuberant.model.onetomany.Employee;
import com.exuberant.util.HibernateUtil;

public class CrudService {

	public <T> void save(T t){
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction(); 
        session.save(t);      
        session.getTransaction().commit(); 
	}

	public void fetchWithHql() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query<Employee> q = session.createQuery("From Employee" , Employee.class);
		List<Employee> resultList = q.getResultList();        
        resultList.stream().forEach(System.err::println);
        Employee getEmp = session.get(Employee.class, 2L);
        System.err.println("Emp using Get: " + getEmp);
        session.close();		
	}

	public void fetchWithId() {
		Session session = HibernateUtil.getSessionFactory().openSession();		
        Employee getEmp = session.get(Employee.class, 2L);
        System.err.println("Emp using Get: " + getEmp);
        session.close();
	}
}
