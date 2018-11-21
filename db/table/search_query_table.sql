CREATE TABLE search_query (
		ID 			 NUMBER NOT NULL PRIMARY KEY,
		subject_name VARCHAR2(255),
		teacher_name VARCHAR2(255),
		max_distance VARCHAR2(255),
		owner 		 NUMBER,
		mod_user     VARCHAR2(300) NOT NULL,
		created_on   TIMESTAMP(6) NOT NULL,
		last_mod     TIMESTAMP(6) NOT NULL,
		dml_flag     VARCHAR2(1) NOT NULL,
		version      NUMBER NOT NULL
);
/
