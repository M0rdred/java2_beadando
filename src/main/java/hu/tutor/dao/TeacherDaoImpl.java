package hu.tutor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.model.Subject;
import hu.tutor.util.HibernateUtil;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public void saveNewSubjectForTeacher(Integer teacherId, Integer subjectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSubjectFromTeacher(Integer teacherId, Integer subjectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void becomeTeacher(Integer userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTeaching(Integer teacherId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Subject> getSubjectsOfTeacher(Integer teacherId) {
		// TODO Auto-generated method stub
		return null;
	}

}
