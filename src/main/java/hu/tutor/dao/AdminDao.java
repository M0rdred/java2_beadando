package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.Teacher;

public interface AdminDao {

	public List<Teacher> getTeachersAwaitingValidation();

	public void enableTeacher(Teacher teacher);
}
