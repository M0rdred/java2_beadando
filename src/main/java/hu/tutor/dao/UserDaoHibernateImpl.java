package hu.tutor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.tutor.model.User;
import hu.tutor.util.HibernateUtil;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserDaoHibernateImpl implements UserDao {

	@Autowired
	private HibernateUtil hibernateUtil;

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

		if (users.size() > 1) {
			throw new RuntimeException("Database inconsistency. More than one user with id: " + id);
		}

		if (users.size() < 1) {
			return new User();
		} else {
			return users.get(0);
		}
	}

	@Override
	public List<User> getAllUsers() {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		List<User> users = criteria.list();
		transaction.commit();
		return users;
	}

	@Override
	public User updateUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		User tmpUser = session.load(User.class, user.getId());

		tmpUser.setFirstName(user.getFirstName());
		tmpUser.setLastName(user.getLastName());
		tmpUser.setAdmin(user.isAdmin());
		tmpUser.setTeacher(user.isTeacher());

		session.persist(tmpUser);
		transaction.commit();

		return this.findUser(user.getId());
	}

	@Override
	public User saveUser(User user) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(user);
		transaction.commit();

		return this.findUser(user.getId());
	}

	@Override
	public void deleteUser(Integer id) {
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		User user = session.load(User.class, id);
		session.delete(user);
		transaction.commit();
	}

}
