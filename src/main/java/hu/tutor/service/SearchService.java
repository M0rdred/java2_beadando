package hu.tutor.service;

import java.util.List;

import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;

public interface SearchService {

	public void saveSearchQuery(SearchQuery query);

	public void deleteSearchQuery(SearchQuery query);

	public List<SearchResult> doSearch(Integer userID, SearchQuery query);

	public void saveResultDistance(String cityFrom, String cityTo, Integer distance);

}
