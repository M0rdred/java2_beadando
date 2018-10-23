package hu.tutor.service;

import java.util.List;

import hu.tutor.model.SearchResult;

public interface SearchService {

	public List<SearchResult> doSearch(Integer userID, String subjectName, String teacherName, Integer maxDistance);

}
