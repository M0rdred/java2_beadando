package hu.tutor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.service.TeacherService;
import hu.tutor.service.exception.UserBlockedException;
import hu.tutor.util.ActiveParameter;
import hu.tutor.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Autowired
	private TeacherService teacherService;

	@Override
	public User findUser(Integer id) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
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
				teacher.setTeachedSubjects(this.teacherService.getSubjectsOfTeacher(teacher.getId()));
				return teacher;
			}
			return user;
		}
	}

	@Override
	public List<User> getAllActiveUsers() {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from User where is_active = :active");
		query.setString("active", ActiveParameter.YES.getValue());
		List<User> users = query.list();
		transaction.commit();
		session.close();
		return users;
	}

	@Override
	public User updateUser(User user) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		session.merge(user);

		transaction.commit();
		session.close();

		return this.findUser(user.getId());
	}

	@Override
	public User saveUser(User user) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(user);
		transaction.commit();
		session.close();

		return this.findUser(user.getId());
	}

	@Override
	public void deleteUser(Integer id) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		User user = session.load(User.class, id);
		session.delete(user);
		transaction.commit();
		session.close();
	}

	@Override
	public User getUserByUserName(String userName) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		Query query = session.createQuery("from User where user_name = :userName ");
		query.setString("userName", userName);
		List<User> users = query.list();
		transaction.commit();
		session.close();

		if (users.size() > 1) {
			throw new RuntimeException("Database inconsistency. More than one user with user name: " + userName);
		}

		if (users.size() < 1) {
			return null;
		} else {
			User user = users.get(0);

			if (!user.getIsActive()) {
				log.error("A felhasználó le van tiltva: " + user.getUserName());
				throw new UserBlockedException("A felhasználó le van tiltva");
			}

			if (user.getClass().equals(Teacher.class)) {
				Teacher teacher = (Teacher) user;
				teacher.setTeachedSubjects(this.teacherService.getSubjectsOfTeacher(teacher.getId()));
				return teacher;
			}
			return user;
		}
	}

}
