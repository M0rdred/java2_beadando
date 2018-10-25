package hu.tutor.dao;

public interface TeachedSubjectDao {
	public void pickUpSubject(Integer teacherId, Integer subjectId);

	public void activateSubject(Integer teacherId, Integer subjectId, boolean active);

	public void modifySubjectDescription(Integer teacherId, Integer subjectId, String description);
}
