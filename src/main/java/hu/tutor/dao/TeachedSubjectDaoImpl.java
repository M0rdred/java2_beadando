package hu.tutor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.util.HibernateUtil;

@Repository
public class TeachedSubjectDaoImpl implements TeachedSubjectDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public void pickUpSubject(Integer teacherId, Integer subjectId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activateSubject(Integer teacherId, Integer subjectId, boolean active) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifySubjectDescription(Integer teacherId, Integer subjectId, String description) {
		// TODO Auto-generated method stub

	}

}
