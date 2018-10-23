package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.SearchResult;

public interface SearchDao {

	public List<SearchResult> doSearch(Integer userID, String subjectName, String teacherName, Integer maxDistance);
}
