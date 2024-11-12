package com.rushikesh.JPA_Demo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App 
{
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    	EntityManager em = emf.createEntityManager();
    	
    	
    	// To fetch the object from DB
    	em.getTransaction().begin();
    	Student obj1 = em.find(Student.class, 1);
    	System.out.println("Fetched Student object-> " + obj1);
    	em.getTransaction().commit();
    	
    	
    	// To save the object in DB
    	Student obj2 = new Student();
        obj2.setSid(9);
        obj2.setSname("Maria");
        obj2.setCourse("Hardware");
    	
    	em.getTransaction().begin();
    	em.persist(obj2);
    	em.getTransaction().commit();
    	System.out.println("Saved Student object-> " + obj2);
    	
    	
    	// To Delete the object from DB
    	em.getTransaction().begin();
    	Student obj3 = em.find(Student.class, 9);
    	em.remove(obj3);
    	System.out.println("Deleted Student object-> " + obj3);
    	em.getTransaction().commit();
    	
    }
}
