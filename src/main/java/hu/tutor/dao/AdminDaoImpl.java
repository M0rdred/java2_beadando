package hu.tutor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
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
	public void activatePerson(Integer personId, ActiveParameter active) {};

	@Override
	public void enableTeacher(Integer teacherId, ActiveParameter enable) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("enableTeacher");

		storedProcedure.setParameter("p_teacher_id", teacherId);
		storedProcedure.setParameter("p_enable", enable.name());

		storedProcedure.execute();

		transaction.commit();
		session.close();
	}

}
