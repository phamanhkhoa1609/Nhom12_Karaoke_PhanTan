package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryUltil {
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;
	
	public EntityManagerFactoryUltil(String name) {
		// TODO Auto-generated constructor stub
		entityManagerFactory = Persistence.createEntityManagerFactory(name);
		entityManager = entityManagerFactory.createEntityManager();
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void closeEntityManager() {
		entityManager.close();
	}
	public void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}
}
