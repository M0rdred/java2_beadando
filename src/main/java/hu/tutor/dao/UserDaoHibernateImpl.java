package hu.tutor.dao;

import java.util.List;

import org.hibernate.Criteria;
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
		return users.get(0);

	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

}
