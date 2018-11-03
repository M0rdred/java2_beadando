package hu.tutor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.model.Subject;
import hu.tutor.model.TeachedSubject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.util.ActiveParameter;
import hu.tutor.util.HibernateUtil;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getTeachersAwaitingValidation() {

		List<Teacher> teacherList = new ArrayList<>();

		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		SQLQuery sqlQuery = session.createSQLQuery("select * from teachers_waiting_validation_vw");

		sqlQuery.addEntity(Teacher.class);

		sqlQuery.list().forEach(record -> {
			teacherList.add((Teacher) record);
		});

		transaction.commit();
		session.close();

		return teacherList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> getSubjectsAwaitingValidation() {

		List<Subject> subjectList = new ArrayList<>();

		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		SQLQuery sqlQuery = session.createSQLQuery("select * from subjects_waiting_validation_vw");

		sqlQuery.addEntity(Subject.class);

		sqlQuery.list().forEach(record -> {
			subjectList.add((Subject) record);
		});

		transaction.commit();
		session.close();

		return subjectList;
	};

	@Override
	public void activatePerson(Integer personId, ActiveParameter active) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("activatePerson");

		storedProcedure.setParameter("p_person_id", personId);
		storedProcedure.setParameter("p_active", active.getValue());

		storedProcedure.execute();

		transaction.commit();
		session.close();
	};

	@Override
	public void enableTeacher(Integer teacherId, ActiveParameter enable) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("enableTeacher");

		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_enable", enable.getValue());

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@Override
	public void enableSubject(Integer subjectId, ActiveParameter enable) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("enableSubject");

		storedProcedure.setParameter("p_subject_id", subjectId);
		storedProcedure.setParameter("p_enable", enable.getValue());

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);

		List<User> users = criteria.list();

		transaction.commit();
		session.close();

		return users;
	}

	@Override
	public void modifyPassword(Integer userId, String newPassword) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("modifyPassword");

		storedProcedure.setParameter("p_user_id", userId);
		storedProcedure.setParameter("p_new_password", newPassword);

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeachedSubject> listTeachedSubjects() {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("listTeachedSubjects");

		long nanoTime = System.nanoTime();
		System.err.println("nanotime: " + nanoTime);

		storedProcedure.setParameter("p_time", nanoTime);

		storedProcedure.execute();
		List<TeachedSubject> resultList = storedProcedure.getResultList();

		transaction.commit();
		session.close();

		return resultList;
	}

	@Override
	public void activateTeachedSubject(Integer subjectId, Integer teacherId, ActiveParameter active) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("activateTeachedSubject");

		storedProcedure.setParameter("p_subject_id", subjectId);
		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_active", active.getValue());

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

}
