package hu.tutor.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NamedQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.service.SubjectServiceImpl;
import hu.tutor.util.HibernateUtil;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
@NamedQuery(name = "getSubjectsOfTeacher", query = "call get_subjects_of_teacher(:t_id)")
public class UserDaoHibernateImpl implements UserDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Autowired
	private SubjectServiceImpl subjectService;

	@Override
	public User findUser(Integer id) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		// Criteria criteria = session.createCriteria(User.class);
		// criteria.add(Restrictions.eq("id", id));
		Query query = session.createQuery("from User where id = :ID");
		query.setInteger("ID", id);
		List<User> users = query.list();
		transaction.commit();
		session.close();

		if (users.size() > 1) {
			throw new RuntimeException("Database inconsistency. More than one user with id: " + id);
		}

		if (users.size() < 1) {
			return null;
		} else {
			User user = users.get(0);
			if (user.getClass().equals(Teacher.class)) {
				Teacher teacher = (Teacher) user;
				teacher.setTeachedSubjects(this.getSubjectsOfTeacher(teacher.getId()));
				return teacher;
			}
			return user;
		}
	}

	@Override
	public List<User> getAllUsers() {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		List<User> users = criteria.list();
		transaction.commit();
		session.close();
		return users;
	}

	@Override
	public User updateUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		User tmpUser = session.load(user.getClass(), user.getId());

		tmpUser.setFirstName(user.getFirstName());
		tmpUser.setLastName(user.getLastName());
		tmpUser.setAdmin(user.isAdmin());
		tmpUser.setTeacher(user.isTeacher());

		if (tmpUser instanceof Teacher && user instanceof Teacher) {
			((Teacher) tmpUser).setTeachedSubjects(((Teacher) user).getTeachedSubjects());
		}

		session.merge(tmpUser);
		transaction.commit();
		session.close();

		return this.findUser(user.getId());
	}

	@Override
	public User saveUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(user);
		transaction.commit();
		session.close();

		return this.findUser(user.getId());
	}

	@Override
	public void deleteUser(Integer id) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		User user = session.load(User.class, id);
		session.delete(user);
		transaction.commit();
		session.close();
	}

	@Override
	public List<Subject> getSubjectsOfTeacher(Integer teacherId) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		Query query = session
				.createSQLQuery("select ts.subject_id from teached_subjects as ts where ts.teacher_id = :t_id")
				.setParameter("t_id", teacherId);

		List<Integer> subjectIds = query.list();

		List<Subject> subjects = new ArrayList<>();
		Subject subject;
		for (Integer id : subjectIds) {
			subject = subjectService.getSubjectById(id);
			subjects.add(subject);
		}
		return subjects;
	}
}
