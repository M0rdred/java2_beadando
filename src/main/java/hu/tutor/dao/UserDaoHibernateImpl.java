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
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		List<User> users = criteria.list();
		transaction.commit();

		if (users.size() > 1) {
			throw new RuntimeException("Database inconsistent. More than one user with id: " + id);
		}

		return users.get(0);

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
