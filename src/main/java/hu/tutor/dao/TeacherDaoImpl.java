package hu.tutor.dao;

import java.util.List;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.model.Subject;
import hu.tutor.util.HibernateUtil;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public void saveNewSubjectForTeacher(Integer teacherId, Integer subjectId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("saveNewSubjectForTeacher");

		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_subject_id", subjectId);

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@Override
	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("deleteSubjectFromTeacher");

		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_subject_id", subjectId);

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@Override
	public void becomeTeacher(Integer userId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("becomeTeacher");

		storedProcedure.setParameter("p_user_id", userId);

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@Override
	public void endTeaching(Integer teacherId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("endTeaching");

		storedProcedure.setParameter("p_teacher_id", teacherId);

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> getSubjectsOfTeacher(Integer teacherId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("getSubjectsOfTeacher");

		storedProcedure.setParameter("p_teacher_id", teacherId);

		storedProcedure.execute();
		List<Subject> subjectList = storedProcedure.getResultList();

		transaction.commit();
		session.close();

		return subjectList;
	}

}
