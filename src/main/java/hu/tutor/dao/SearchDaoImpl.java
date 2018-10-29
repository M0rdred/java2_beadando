package hu.tutor.dao;

import java.util.List;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.model.search.SearchQuery;
import hu.tutor.model.search.SearchResult;
import hu.tutor.util.HibernateUtil;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchResult> doSearch(Integer userId, SearchQuery query) {

		String subjectName = query.getSubjectName();
		String teacherName = query.getTeacherName();
		Integer maxDistance = query.getMaxDistance();
		System.err.println(subjectName + " " + teacherName + " " + maxDistance);

		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		StoredProcedureQuery storedProcedure = this.hibernateUtil.getEntityManager()
				.createNamedStoredProcedureQuery("loadSearchResults");

		storedProcedure.setParameter("p_searcher_id", userId);
		storedProcedure.setParameter("p_subject_name", subjectName);
		storedProcedure.setParameter("p_teacher_name", teacherName);
		storedProcedure.setParameter("p_max_distance", maxDistance);

		storedProcedure.execute();
		List<SearchResult> resultList = storedProcedure.getResultList();

		transaction.commit();
		session.close();

		return resultList;
	}

	@Override
	public void saveSearchQuery(SearchQuery query) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		session.persist(query);

		transaction.commit();
		session.close();
	}

	@Override
	public void deleteSearchQuery(SearchQuery query) {
		Session session = this.hibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		session.delete(query);

		transaction.commit();
		session.close();
	}

}
