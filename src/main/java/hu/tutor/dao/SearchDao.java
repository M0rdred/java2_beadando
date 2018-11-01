package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;

public interface SearchDao {

	public void deleteSearchQuery(SearchQuery query);

	public List<SearchResult> doSearch(Integer userId, SearchQuery query);

	public void saveSearchQuery(SearchQuery query);

	public void saveResultDistance(String cityFrom, String cityTo, Integer distance);

}
