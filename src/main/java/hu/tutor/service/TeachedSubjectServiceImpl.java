package hu.tutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.TeachedSubjectDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeachedSubjectServiceImpl implements TeachedSubjectService {

	@Autowired
	private TeachedSubjectDao teachedSubjectDao;

	@Override
	public void pickUpSubject(Integer teacherId, Integer subjectId) {
		try {
			this.teachedSubjectDao.pickUpSubject(teacherId, subjectId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void activateSubject(Integer teacherId, Integer subjectId, boolean active) {
		try {
			this.teachedSubjectDao.activateSubject(teacherId, subjectId, active);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void modifySubjectDescription(Integer teacherId, Integer subjectId, String description) {
		try {
			this.teachedSubjectDao.modifySubjectDescription(teacherId, subjectId, description);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public String getSubjectDescription(Integer subjectId, Integer teacherId) {
		try {
			return this.teachedSubjectDao.getSubjectDescription(teacherId, subjectId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
