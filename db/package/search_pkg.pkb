-- search_pkg body
CREATE OR REPLACE PACKAGE BODY search_pkg AS
  FUNCTION search(p_searcher_id  NUMBER,
                  p_subject_name VARCHAR2,
                  p_teacher_name VARCHAR2,
                  p_max_distance NUMBER) RETURN ty_search_result_table IS
    l_sql                 VARCHAR(1000);
    l_search_temp_table   ty_search_result_table;
    l_search_result_table ty_search_result_table;
    l_search_for_close    BOOLEAN;
    l_origin_city         VARCHAR2(100);
    l_distance            NUMBER;
  BEGIN
    l_search_for_close := FALSE;
  
    l_sql := 'select ty_search_result(p.first_name,
                        p.last_name,
                        a.country,
                        a.city,
                        a.street,
                        a.house_number,
                        t.text,
                        s.name,
                        sub_trans.text,
                        tt.text,
                        0)
  from person p
  join address a
    on p.address_id = a.id
  join teached_subject ts
    on p.id = ts.teacher_id
  join subject s
    on ts.subject_id = s.id
  join translation t
    on p.introduction = t.id
  join translation sub_trans
    on s.description = sub_trans.id
  join translation tt
    on ts.description = tt.id
 where p.active = ' || chr(39) || 'Y' || chr(39) || '
   and s.active = ' || chr(39) || 'Y' || chr(39) || '
   and ts.active = ' || chr(39) || 'Y' || chr(39);
  
    -- search for subject
    IF p_subject_name IS NOT NULL
       AND length(TRIM(p_subject_name)) > 0
    THEN
    
      l_sql := l_sql || ' and lower(s.name) like ' || chr(39) || '%' ||
               lower(p_subject_name) || '%' || chr(39);
    END IF;
  
    -- search for teacher
    IF p_teacher_name IS NOT NULL
       AND length(TRIM(p_teacher_name)) > 0
    THEN
    
      l_sql := l_sql || ' and lower(p.first_name || p.last_name) like ' ||
               chr(39) || '%' || lower(p_teacher_name) || '%' || chr(39);
    END IF;
  
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
        l_distance         := distance_pkg.get_distance(l_origin_city, l_search_temp_table(i).city);
      
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
  
    RETURN l_search_result_table;
  END;
END search_pkg;
/
