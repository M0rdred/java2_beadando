package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.AdminDao;
import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;
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
			this.adminDao.getSubjectsAwaitingValidation();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		return null;
	}

	@Override
	public void activatePerson(Integer personId) {
		try {
			this.adminDao.activatePerson(personId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public void enableTeacher(Integer teacherId) {
		try {
			this.adminDao.enableTeacher(teacherId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

}
