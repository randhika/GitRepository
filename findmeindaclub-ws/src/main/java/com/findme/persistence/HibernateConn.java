package com.findme.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.findme.model.Usuario;

public class HibernateConn {
	private static Configuration configuration; 
	private static ServiceRegistry service; 
	private static SessionFactory sessionFactory; 
	
	static {
		try{
			
			configuration = new Configuration().
					setProperty("hibernate.connection.driver_class", "com.mysql.Driver").
					setProperty("hibernate.connection.password", "").
					setProperty("hibernate.connection.url" , "").
					setProperty("hibernate.connection.password", "").
					setProperty("hibernate.dialect" , "").
					setProperty("hibernate.show_sql" , "true").
//					Setting Pool properties
					setProperty("hibernate.c3p0.min_size", "5").
					setProperty("hibernate.c3p0.max_size", "20").
					setProperty("hibernate.c3p0.timeout", "1800").
					setProperty("hibernate.c3p0.max_statements", "50").
//					Adding Annoted Classes
					addAnnotatedClass(Usuario.class).
					configure();
			
			service = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(service);
		}catch (Exception e){
			System.out.println("ERRO ao criar sessao");
			e.printStackTrace();
		}
	}
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
}
