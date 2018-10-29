package hu.tutor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.SearchDao;
import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public List<SearchResult> doSearch(Integer userID, SearchQuery query) {
		try {
			return this.searchDao.doSearch(userID, query);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

}
