CREATE OR REPLACE PACKAGE BODY search_pkg AS
  PROCEDURE search(p_searcher_id  IN NUMBER
                  ,p_subject_name IN VARCHAR2
                  ,p_teacher_name IN VARCHAR2
                  ,p_max_distance IN NUMBER
                  ,p_result_list  OUT SYS_REFCURSOR) IS
    l_sql                 VARCHAR(4000);
    l_search_temp_table   ty_search_result_table;
    l_search_result_table ty_search_result_table;
    l_search_for_close    BOOLEAN;
    l_origin_city         VARCHAR2(100);
    l_distance            NUMBER;
  BEGIN
    l_search_for_close := FALSE;
  
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
                        personal_subject_description => '''',
                        distance                     => 0)
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
    
      l_sql := l_sql || ' AND lower(s.name) LIKE ' || chr(39) || '%' ||
               lower(p_subject_name) || '%' || chr(39);
    END IF;
  
    -- search for teacher
    IF p_teacher_name IS NOT NULL
       AND length(TRIM(p_teacher_name)) > 0
    THEN
    
      l_sql := l_sql || ' AND lower(p.first_name || ' || chr(39) || chr(32) ||
               chr(39) || ' || p.last_name) LIKE ' || chr(39) || '%' ||
               lower(p_teacher_name) || '%' || chr(39);
    END IF;
  
    BEGIN
      INSERT INTO logs (log_entry) VALUES (l_sql);
      COMMIT;
    END;
  
    EXECUTE IMMEDIATE l_sql BULK COLLECT
      INTO l_search_temp_table;
  
    l_search_result_table := ty_search_result_table();
  
    -- filter for max distance
    IF p_max_distance IS NOT NULL
       AND p_max_distance >= 0
    THEN
    
      SELECT a.city
        INTO l_origin_city
        FROM person p
        JOIN address a
          ON p.address_id = a.id
       WHERE p.id = p_searcher_id;
    
      FOR i IN l_search_temp_table.first .. l_search_temp_table.last
      LOOP
        l_search_for_close := TRUE;
        l_distance         := distance_pkg.get_distance(l_origin_city,
                                                        l_search_temp_table(i).city);
      
        l_distance := l_distance / 1000;
        IF l_distance <= p_max_distance
        THEN
          l_search_result_table.extend;
          l_search_result_table(l_search_result_table.count) := l_search_temp_table(i);
          l_search_result_table(l_search_result_table.count).distance := l_distance ||
                                                                         ' km';
        
        END IF;
      END LOOP;
    END IF;
  
    IF l_search_result_table.count = 0
       AND NOT l_search_for_close
    THEN
      l_search_result_table := l_search_temp_table;
    END IF;
  
    OPEN p_result_list FOR
      SELECT *
        FROM TABLE(CAST(l_search_result_table AS ty_search_result_table));
  END;
END search_pkg;
/
