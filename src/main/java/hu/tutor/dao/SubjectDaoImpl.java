package hu.tutor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.tutor.model.Subject;
import hu.tutor.util.HibernateUtil;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@SuppressWarnings("unchecked")
public class SubjectDaoImpl implements SubjectDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Subject getSubject(Integer subjectId) {
		Session session = hibernateUtil.getSessionFactory().openSession();
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
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		Criteria criteria = session.createCriteria(Subject.class);
		List<Subject> subjects = criteria.list();

		transaction.commit();
		session.close();

		return subjects;
	}

	@Override
	public void saveNewSubject(Subject subject) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(subject);
		transaction.commit();
		session.close();
	}

}
