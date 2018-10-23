package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.SearchDao;
import hu.tutor.model.SearchResult;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public List<SearchResult> doSearch(Integer userID, String subjectName, String teacherName, Integer maxDistance) {
		return this.searchDao.doSearch(userID, subjectName, teacherName, maxDistance);
	}

}
