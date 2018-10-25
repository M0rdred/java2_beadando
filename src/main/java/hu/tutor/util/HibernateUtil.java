package hu.tutor.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
	private SessionFactory sessionFactory;
	private EntityManager entityManager;

	@Autowired
	public HibernateUtil(EntityManagerFactory factory) {
		SessionFactory sessionFactory = factory.unwrap(SessionFactory.class);
		if (sessionFactory == null) {
			throw new RuntimeException("Not Hibernate SessionFactory");
		} else {
			this.sessionFactory = sessionFactory;
		}

		this.entityManager = factory.createEntityManager();
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
