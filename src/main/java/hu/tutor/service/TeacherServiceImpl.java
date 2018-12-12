package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.TeacherDao;
import hu.tutor.model.TeachedSubject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public void saveNewSubjectForTeacher(Integer teacherId, Integer subjectId) {
		try {
			this.teacherDao.saveNewSubjectForTeacher(teacherId, subjectId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId) {
		try {
			this.teacherDao.deleteSubjectFromTeacher(teacherId, subjectId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void becomeTeacher(Integer userId) {
		try {
			this.teacherDao.becomeTeacher(userId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void endTeaching(Integer teacherId) {
		try {
			this.teacherDao.endTeaching(teacherId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<TeachedSubject> getSubjectsOfTeacher(Integer teacherId) {
		try {
			return this.teacherDao.getSubjectsOfTeacher(teacherId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
