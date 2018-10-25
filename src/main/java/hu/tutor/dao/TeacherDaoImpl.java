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
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void becomeTeacher(Integer userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTeaching(Integer teacherId) {
		// TODO Auto-generated method stub

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
