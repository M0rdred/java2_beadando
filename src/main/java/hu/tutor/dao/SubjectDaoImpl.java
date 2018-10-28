package hu.tutor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.tutor.model.Subject;
import hu.tutor.util.ActiveParameter;
import hu.tutor.util.HibernateUtil;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@SuppressWarnings("unchecked")
public class SubjectDaoImpl implements SubjectDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Subject getSubject(Integer subjectId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("from Subject where id = :ID");
		query.setInteger("ID", subjectId);
		List<Subject> subjects = query.list();
		transaction.commit();
		session.close();

		if (subjects.size() > 1) {
			throw new RuntimeException("Database inconsistency. More than one subject with id: " + subjectId);
		}

		if (subjects.size() < 1) {
			return null;
		} else {
			return subjects.get(0);
		}
	}

	@Override
	public List<Subject> getAllSubjects() {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("from Subject where active = :active");
		query.setString("active", ActiveParameter.YES.getValue());
		List<Subject> subjects = query.list();

		transaction.commit();
		session.close();

		return subjects;
	}

	@Override
	public void saveNewSubject(Subject subject) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		session.persist(subject);

		transaction.commit();
		session.close();
	}

	@Override
	public void modifySubject(Subject subject) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		session.merge(subject);

		transaction.commit();
		session.close();
	}

	@Override
	public void deleteSubject(Integer subjectId) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		SQLQuery sqlQuery = session.createSQLQuery("update Subject set dml_flag = 'D' where id = :subject_id");
		sqlQuery.setParameter("subject_id", subjectId);

		sqlQuery.executeUpdate();

		transaction.commit();
		session.close();
	}

}
