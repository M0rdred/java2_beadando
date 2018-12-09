package hu.tutor.service;

public interface TeachedSubjectService {
	public void pickUpSubject(Integer teacherId, Integer subjectId);

	public void activateSubject(Integer teacherId, Integer subjectId, boolean active);

	public void modifySubjectDescription(Integer teacherId, Integer subjectId, String description);

	public String getSubjectDescription(Integer subjectId, Integer teacherId);
}
