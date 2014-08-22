package com.connectcard.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




import com.connectcard.domain.City;
import com.connectcard.domain.Matchup;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration()
								.configure()
								.addPackage("com.connectcard.domain") //the fully qualified package name
								.addAnnotatedClass(City.class)
								.addAnnotatedClass(Matchup.class)
								.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}