package hu.tutor.dao;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.util.HibernateUtil;

@Repository
public class TeachedSubjectDaoImpl implements TeachedSubjectDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public void pickUpSubject(Integer teacherId, Integer subjectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activateSubject(Integer teacherId, Integer subjectId, boolean active) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifySubjectDescription(Integer teacherId, Integer subjectId, String description) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("modifySubjectDescription");

		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_subject_id", subjectId);
		storedProcedure.setParameter("p_description", description);

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@Override
	public String getSubjectDescription(Integer teacherId, Integer subjectId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("getSubjectDescription");

		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_subject_id", subjectId);

		storedProcedure.execute();
		String description = storedProcedure.getOutputParameterValue("p_description").toString();

		transaction.commit();
		session.close();

		return description;
	}

}
