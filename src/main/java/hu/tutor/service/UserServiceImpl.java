package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hu.tutor.dao.UserDaoHibernateImpl;
import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoHibernateImpl userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<User> listUsers() {
		return this.userDao.getAllUsers();
	}

	@Override
	public User getUserById(Integer id) {
		return this.userDao.findUser(id);
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		return this.userDao.saveUser(user);
	}

	@Override
	public User updateUser(User user) {
		return this.userDao.updateUser(user);
	}

	@Override
	public void deleteUser(Integer id) {
		this.userDao.deleteUser(id);
	}

	@Override
	public User loadUserByUserName(String userName) {
		return this.userDao.getUserByUserName(userName);
	}

	@Override
	public void saveSubjectOfTeacher(Subject subject) {
		this.userDao.saveSubjectOfTeacher(subject);
	}

	@Override
	public void saveNewSubjectForTeacher(Integer teacherId, Integer subjectId) {
		this.userDao.saveNewSubjectForTeacher(teacherId, subjectId);
	}

	@Override
	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId) {
		this.userDao.deleteSubjectFromTeacher(teacherId, subjectId);
	}

	@Override
	public void becomeTeacher(Integer userId) {
		this.userDao.becomeTeacher(userId);
	}

	@Override
	public void endTeaching(Integer teacherId) {
		this.userDao.endTeaching(teacherId);
	}

	@Override
	public List<Subject> getSubjectsOfTeacher(Teacher teacher) {
		return this.userDao.getSubjectsOfTeacher(teacher.getId());
	}

}
