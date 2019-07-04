package com.antonromanov.temprest.repositoty;

import com.antonromanov.temprest.model.Logs;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
@Transactional
public class DAO {

	@PersistenceContext
	private EntityManager entityManager;


	public void create(Logs logs) {
		entityManager.persist(logs);
	}

	public void testInsert() {

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("postgres.arduino.addlog")
				.registerStoredProcedureParameter("addlog", String.class, ParameterMode.OUT)
				.registerStoredProcedureParameter("temp", Long.class, ParameterMode.IN).setParameter("temp", 174L);

		query.execute();
	}

	public void testRefCursors() {

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("postgres.arduino.todaylogs")
				.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);

		query.execute();

		List<Object[]> logs = query.getResultList();
		logs.forEach(System.out::println);
	}




}
