package hu.tutor.dao;

import java.util.List;

import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;

public interface SearchDao {

	public List<SearchResult> doSearch(Integer userId, SearchQuery query);

	public void saveSearchQuery(SearchQuery query);

	public void deleteSearchQuery(SearchQuery query);
}
