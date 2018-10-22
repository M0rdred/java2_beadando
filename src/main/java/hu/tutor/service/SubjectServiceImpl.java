package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.SubjectDaoImpl;
import hu.tutor.model.Subject;
import hu.tutor.model.Teacher;

@Service("subjectServiceImpl")
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDaoImpl subjectDao;

	@Override
	public Subject getSubjectById(Integer id) {
		return this.subjectDao.getSubject(id);
	}

	@Override
	public List<Subject> getAllSubjects() {
		return this.subjectDao.getAllSubjects();
	}

	@Override
	public void saveNewSubject(Subject subject) {
		this.subjectDao.saveNewSubject(subject);
	}

	@Override
	public List<Subject> getSubjectsOfTeacher(Teacher teacher) {
		return this.subjectDao.getSubjectsOfTeacher(teacher);
	}

}
