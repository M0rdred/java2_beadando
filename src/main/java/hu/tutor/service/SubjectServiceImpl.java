package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.SubjectDao;
import hu.tutor.model.Subject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDao subjectDao;

	@Override
	public Subject getSubjectById(Integer id) {
		try {
			return this.subjectDao.getSubject(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public List<Subject> getAllSubjects(boolean onlyActive) {
		try {
			return this.subjectDao.getAllSubjects(onlyActive);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void saveNewSubject(Subject subject) {
		try {
			this.subjectDao.saveNewSubject(subject);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public void modifySubject(Subject subject) {
		try {
			this.subjectDao.modifySubject(subject);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public void deleteSubject(Integer subjectId) {
		try {
			this.subjectDao.deleteSubject(subjectId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

}
