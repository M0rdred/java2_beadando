package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.SubjectDaoImpl;
import hu.tutor.model.Subject;

@Service("subjectServiceImpl")
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDaoImpl subjectDao;

	@Override
	public Subject getSubjectById(Integer id) {
		return subjectDao.getSubject(id);
	}

	@Override
	public List<Subject> getAllSubjects() {
		return subjectDao.getAllSubjects();
	}

	@Override
	public void saveNewSubject(Subject subject) {
		subjectDao.saveNewSubject(subject);
	}

}
