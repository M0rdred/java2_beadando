CREATE OR REPLACE PACKAGE BODY search_pkg AS
  PROCEDURE search(p_searcher_id  IN NUMBER
                  ,p_subject_name IN VARCHAR2
                  ,p_teacher_name IN VARCHAR2
                  ,p_max_distance IN NUMBER
                  ,p_result_list  OUT SYS_REFCURSOR) IS
    l_sql                 VARCHAR(4000);
    l_search_temp_table   ty_search_result_table;
    l_search_result_table ty_search_result_table;
    l_search_for_distance BOOLEAN;
    l_origin_city         VARCHAR2(255);
    l_distance            NUMBER;
  BEGIN
    l_search_for_distance := FALSE;
  
    BEGIN
      SELECT a.city
        INTO l_origin_city
        FROM address a
       WHERE a.dml_flag <> 'D'
         AND a.id = (SELECT p.address_id
                       FROM person p
                      WHERE p.dml_flag <> 'D'
                        AND p.id = p_searcher_id);
    
    EXCEPTION
      WHEN no_data_found THEN
        l_origin_city := NULL;
      
    END;
  
    l_sql := 'SELECT ty_search_result(id             => rownum,
                        first_name                   => p.first_name,
                        last_name                    => p.last_name,
                        country                      => a.country,
                        zip                          => a.zip,
                        city                         => a.city,
                        street                       => a.street,
                        house_number                 => a.house_number,
                        phone_number                 => p.phone,
                        email                        => p.email,
                        introduction                 => p.introduction,
                        subject_name                 => s.name,
                        subject_description          => s.description,
                        personal_subject_description => ts.description,
                        distance                     => distance_pkg.get_distance(:1, a.city))
  FROM person p
  JOIN address a
    ON p.address_id = a.id
  JOIN teached_subject ts
    ON p.id = ts.teacher_id
  JOIN subject s
    ON ts.subject_id = s.id
 WHERE p.dml_flag <> ' || chr(39) || 'D' || chr(39) || chr(10) ||
             'AND s.active = ' || chr(39) || 'Y' || chr(39) || chr(10) ||
             'AND ts.active = ' || chr(39) || 'Y' || chr(39);
  
    -- search for subject
    IF p_subject_name IS NOT NULL
       AND length(TRIM(p_subject_name)) > 0
    THEN
    
      l_sql := l_sql || ' AND lower(s.name) LIKE ' || ':2';
    ELSE
      l_sql := l_sql || 'AND ((1 = 1) OR :2 IS NULL) ';
    END IF;
  
    -- search for teacher
    IF p_teacher_name IS NOT NULL
       AND length(TRIM(p_teacher_name)) > 0
    THEN
    
      l_sql := l_sql || ' AND lower(p.last_name || ' || chr(39) || chr(32) ||
               chr(39) || ' || p.first_name) LIKE ' || ':3';
    ELSE
      l_sql := l_sql || 'AND ((1 = 1) OR :3 IS NULL) ';
    END IF;
  
    BEGIN
      INSERT INTO logs
        (log_entry)
      VALUES
        ('inputs: ' || p_searcher_id || ',' || p_max_distance || ', ' ||
         l_origin_city || ', ' || p_subject_name || ', ' || p_teacher_name);
      INSERT INTO logs (log_entry) VALUES ('SQL: ' || l_sql);
      COMMIT;
    END;
  
    EXECUTE IMMEDIATE l_sql BULK COLLECT
      INTO l_search_temp_table
      USING l_origin_city, '%' || lower(p_subject_name) || '%', '%' || lower(p_teacher_name) || '%';
  
    l_search_result_table := ty_search_result_table();
  
    -- filter for max distance
    IF p_max_distance IS NOT NULL
       AND p_max_distance > 0
       AND p_searcher_id IS NOT NULL
    THEN
    
      SELECT a.city
        INTO l_origin_city
        FROM person p
        JOIN address a
          ON p.address_id = a.id
       WHERE p.id = p_searcher_id;
    
      FOR i IN l_search_temp_table.first .. l_search_temp_table.last
      LOOP
        l_search_for_distance := TRUE;
        l_distance            := distance_pkg.get_distance(l_origin_city,
                                                           l_search_temp_table(i).city);
      
        IF l_distance <= p_max_distance
        THEN
          l_search_result_table.extend;
          l_search_result_table(l_search_result_table.count) := l_search_temp_table(i);
          l_search_result_table(l_search_result_table.count).distance := l_distance;
        
        END IF;
      END LOOP;
    END IF;
  
    IF NOT l_search_for_distance
    THEN
      l_search_result_table := l_search_temp_table;
    END IF;
  
    OPEN p_result_list FOR
      SELECT *
        FROM TABLE(CAST(l_search_result_table AS ty_search_result_table));
  END;
END search_pkg;
/
