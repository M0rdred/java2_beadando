package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.AdminDao;
import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
import hu.tutor.model.User;
import hu.tutor.util.ActiveParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

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
	public List<User> getAllUsers() {
		try {
			return this.adminDao.getAllUsers();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
