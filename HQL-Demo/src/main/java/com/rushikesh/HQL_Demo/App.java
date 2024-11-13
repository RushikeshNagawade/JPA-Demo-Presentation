package com.rushikesh.HQL_Demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import com.rushikesh.HQL_Demo.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

public class App {
	public static void main(String[] args) {
		// local SessionFactory bean created
//		Configuration config = new Configuration().configure("hibernate.cfg.xml");
//		SessionFactory sessionFactory = config.buildSessionFactory();
//		Session session = sessionFactory.openSession();

		// Load the configuration and build the SessionFactory
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = session.beginTransaction();

		// Insert into DB
		Category electronics = new Category();
		electronics.setName("Electronics");

		Category furniture = new Category();
		furniture.setName("Furniture");

		session.save(electronics);
		session.save(furniture);

		session.save(new Product("Laptop", 1000.00, electronics));
		session.save(new Product("Phone", 500.00, electronics));
		session.save(new Product("Table", 300.00, furniture));

		// Fetch Data from DB
		
		// Using JPA find method
		Product fetchedProduct = session.find(Product.class, 1);
		System.out.println("Product fetched using find method : " + fetchedProduct);
		
		fetchedProduct.setName("Desktop");//Value changed as Object is still in Persistent state
		
		// Using HQL
		Query q = session.createQuery("from Product p where p.id= :i"); 
		q.setParameter("i", 1);
		Product p = (Product) q.uniqueResult();
		System.out.println("Product fetched using HQL query: " + p);
		
		Query q2 = session.createQuery("update Product p set p.price = :pr where name = :na"); 
		q2.setParameter("pr", "400");
		q2.setParameter("na", "Phone");
		int status = q2.executeUpdate();
		System.out.println("Product updated using HQL query: " + status);
		
		// Using Named Query
		Query<Product> query1 = session.createNamedQuery("Product.findAll", Product.class);
		List<Product> products = query1.getResultList();
		System.out.println("Product fetched using Named query:");
		products.forEach(System.out::println);
		
		tx.commit();

		// Close the session
		session.close();

		// Shut down the SessionFactory
		HibernateUtil.shutdown();
	}
}