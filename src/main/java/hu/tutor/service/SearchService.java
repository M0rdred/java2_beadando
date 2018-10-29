package hu.tutor.service;

import java.util.List;

import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;

public interface SearchService {

	public List<SearchResult> doSearch(Integer userID, SearchQuery query);

}
