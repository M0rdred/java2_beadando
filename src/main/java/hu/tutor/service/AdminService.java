package hu.tutor.service;

import java.util.List;

import hu.tutor.model.Teacher;

public interface AdminService {
	public List<Teacher> getTeachersAwaitingValidation();

	public void enableTeacher(Teacher teacher);
}
