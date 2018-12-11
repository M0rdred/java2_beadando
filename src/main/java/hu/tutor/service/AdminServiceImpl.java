package hu.tutor.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.AdminDao;
import hu.tutor.model.Subject;
import hu.tutor.model.TeachedSubject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.security.AuthService;
import hu.tutor.util.ActiveParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private AuthService authService;

	@Override
	public List<Teacher> getTeachersAwaitingValidation() {
		try {
			return this.adminDao.getTeachersAwaitingValidation();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<Subject> getSubjectsAwaitingValidation() {
		try {
			return this.adminDao.getSubjectsAwaitingValidation();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void activatePerson(Integer personId, ActiveParameter active) {
		try {
			this.adminDao.activatePerson(personId, active);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public void enableTeacher(Integer teacherId, ActiveParameter enable) {
		try {
			this.adminDao.enableTeacher(teacherId, enable);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public void enableSubject(Integer subjectId, ActiveParameter enable) {
		try {
			this.adminDao.enableSubject(subjectId, enable);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Set<User> getAllUsers() {
		try {
			return this.adminDao.getAllUsers();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void modifyPassword(Integer userId, String rawPassword) {
		try {
			String encodedPassword = this.authService.encodePassword(rawPassword);

			this.adminDao.modifyPassword(userId, encodedPassword);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<TeachedSubject> listTeachedSubjects() {
		try {
			return this.adminDao.listTeachedSubjects();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void activateTeachedSubject(Integer subjectId, Integer teacherId, ActiveParameter active) {
		try {
			this.adminDao.activateTeachedSubject(subjectId, teacherId, active);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
