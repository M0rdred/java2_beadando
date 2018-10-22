package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.AdminDao;
import hu.tutor.model.Teacher;

@Service("adminServiceImpl")
public class AdminServiceImple implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public List<Teacher> getTeachersAwaitingValidation() {
		return this.adminDao.getTeachersAwaitingValidation();
	}

	@Override
	public void enableTeacher(Teacher teacher) {
		this.adminDao.enableTeacher(teacher);
	}

}
