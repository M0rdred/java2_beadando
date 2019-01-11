ALTER TABLE search_query add CONSTRAINT fk_search_query_person foreign KEY(owner_id) references person(id)
/
