-- distance_pkg body
CREATE OR REPLACE PACKAGE BODY distance_pkg AS

  FUNCTION distance_from_google_maps_api(p_city_from IN VARCHAR2,
                                         p_city_to   IN VARCHAR2) RETURN NUMBER IS
    v_distance NUMBER;
    v_status   VARCHAR2(100);
  BEGIN
  
    INSERT INTO temp
      SELECT httpuritype('http://maps.googleapis.com/maps/api/distancematrix/xml?origins=' || p_city_from || chr(38) || 'destinations=' || p_city_to)
             .getclob()
        FROM dual;
  
    SELECT extractvalue(xmltype(text), '/DistanceMatrixResponse/row/element/status')
      INTO v_status
      FROM temp;
  
    IF v_status IS NOT NULL
       AND v_status = 'OK'
    THEN
    
      SELECT extractvalue(xmltype(text), '/DistanceMatrixResponse/row/element/distance/value')
        INTO v_distance
        FROM temp;
      IF v_distance IS NOT NULL
      THEN
        INSERT INTO city_distance
          (city_from,
           city_to,
           distance,
           last_mod)
        VALUES
          (p_city_from,
           p_city_to,
           v_distance,
           SYSDATE);
      END IF;
      COMMIT;
    END IF;
    RETURN v_distance;
  END;

  FUNCTION get_distance(p_city_from IN VARCHAR2,
                        p_city_to   IN VARCHAR2) RETURN NUMBER IS
    v_distance NUMBER;
  BEGIN
  
    IF p_city_from IS NOT NULL
       AND p_city_to IS NOT NULL
    THEN
      /* select cdo.distance        into v_distance        from (select cdi.city_from, city_to, distance                from city_distance cdi               where cdi.city_from like p_city_from) cdo       where cdo.city_to like p_city_to;                        if v_distance is null then                 select cdo.distance          into v_distance          from (select cdi.city_from, city_to, distance                  from city_distance cdi                 where cdi.city_from like p_city_to) cdo         where cdo.city_to like p_city_from;      end if;*/
      IF p_city_from = p_city_to
      THEN
        RETURN 0;
      END IF;
    
      FOR c IN (SELECT cd.city_from, cd.city_to, cd.distance
                  FROM city_distance cd
                 WHERE cd.city_from = p_city_from)
      LOOP
        IF c.city_to = p_city_to
        THEN
          v_distance := c.distance;
          EXIT;
        END IF;
      END LOOP;
    
      IF v_distance IS NULL
      THEN
        FOR c IN (SELECT cd.city_from, cd.city_to, cd.distance
                    FROM city_distance cd
                   WHERE cd.city_from = p_city_to)
        LOOP
          IF c.city_to = p_city_from
          THEN
            v_distance := c.distance;
            EXIT;
          END IF;
        END LOOP;
      END IF;
    
      IF v_distance IS NULL
      THEN
        v_distance := distance_from_google_maps_api(p_city_from, p_city_to);
      END IF;
    END IF;
    RETURN v_distance;
  END;

  PROCEDURE update_distances IS
    v_distance NUMBER;
    v_status   VARCHAR2(100);
  BEGIN
    FOR upd IN (SELECT c.*
                  FROM city_distance c
                 WHERE trunc(SYSDATE) - trunc(c.last_mod) > 10)
    LOOP
      INSERT INTO temp
        SELECT httpuritype('http://maps.googleapis.com/maps/api/distancematrix/xml?origins=' || upd.city_from || chr(38) || 'destinations=' || upd.city_to)
               .getclob()
          FROM dual;
    
      SELECT extractvalue(xmltype(text), '/DistanceMatrixResponse/row/element/status')
        INTO v_status
        FROM temp;
    
      IF v_status IS NOT NULL
         AND v_status = 'OK'
      THEN
      
        SELECT extractvalue(xmltype(text), '/DistanceMatrixResponse/row/element/distance/value')
          INTO v_distance
          FROM temp;
      
        IF v_distance IS NOT NULL
        THEN
          UPDATE city_distance
             SET distance = v_distance,
                 last_mod = SYSDATE
           WHERE city_from = upd.city_from
             AND city_to = upd.city_to;
        END IF;
      END IF;
      COMMIT;
    END LOOP;
  END;
END distance_pkg;
/

-- subject_pkg body
CREATE OR REPLACE PACKAGE BODY subject_pkg IS

  PROCEDURE new_subject(p_name        VARCHAR2,
                        p_description VARCHAR2) IS
    subject_already_exists_ex EXCEPTION;
    v_subject_count NUMBER;
  BEGIN
    IF p_name IS NOT NULL
       AND length(p_name) > 3
    THEN
      SELECT COUNT(*)
        INTO v_subject_count
        FROM subject s
       WHERE s.name = p_name;
      IF v_subject_count > 0
      THEN
        -- raise exception subject already exists
        RAISE subject_already_exists_ex;
      END IF;
    
      IF p_description IS NOT NULL
         AND length(p_description) > 5
      THEN
        SET TRANSACTION NAME 'new_subject';
        INSERT INTO translation
          (LANGUAGE,
           text)
        VALUES
          (100,
           p_description);
      
        INSERT INTO subject
          (NAME,
           description)
        VALUES
          (p_name,
           translation_sq.currval);
        COMMIT;
      END IF;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  EXCEPTION
    WHEN subject_already_exists_ex THEN
      raise_application_error(-20001, 'Subject name already exists in database.');
  END;

  PROCEDURE modify_subject(p_id          NUMBER,
                           p_active      VARCHAR2,
                           p_description VARCHAR2) IS
  BEGIN
    IF p_id IS NOT NULL
    THEN
      SET TRANSACTION NAME 'modify_subject';
      UPDATE subject s
         SET s.active = p_active
       WHERE s.id = p_id;
      UPDATE translation t
         SET t.text = p_description
       WHERE t.id = (SELECT s.description
                       FROM subject s
                      WHERE s.id = p_id);
      COMMIT;
    END IF;
  END;

  PROCEDURE delete_subject(p_id NUMBER) IS
  BEGIN
    IF p_id IS NOT NULL
    THEN
      UPDATE subject s
         SET s.active = 'N'
       WHERE s.id = p_id;
    END IF;
  END;

END subject_pkg;
/

-- adress_pkg body
CREATE OR REPLACE PACKAGE BODY adress_pkg AS
  PROCEDURE new_adress(p_country      VARCHAR2,
                       p_city         VARCHAR2,
                       p_street       VARCHAR2,
                       p_house_number VARCHAR2) IS
    l_adress_count NUMBER;
  BEGIN
    IF p_country IS NOT NULL
       AND p_city IS NOT NULL
       AND p_street IS NOT NULL
       AND p_house_number IS NOT NULL
    THEN
    
      SELECT COUNT(*)
        INTO l_adress_count
        FROM adress a
       WHERE a.country = p_country
         AND a.city = p_city
         AND a.street = p_street
         AND a.house_number = p_house_number;
    
      IF l_adress_count > 0
      THEN
        raise_application_error(-20001, 'The given adress is already existing in the database');
      ELSE
        INSERT INTO adress
          (country,
           city,
           street,
           house_number)
        VALUES
          (p_country,
           p_city,
           p_street,
           p_house_number);
      END IF;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE modify_adress(p_id           NUMBER,
                          p_country      VARCHAR2,
                          p_city         VARCHAR2,
                          p_street       VARCHAR2,
                          p_house_number VARCHAR2) IS
  BEGIN
    IF p_id IS NOT NULL
       AND p_country IS NOT NULL
       AND p_city IS NOT NULL
       AND p_street IS NOT NULL
       AND p_house_number IS NOT NULL
    THEN
      UPDATE adress a
         SET a.country      = p_country,
             a.city         = p_city,
             a.street       = p_street,
             a.house_number = p_house_number
       WHERE a.id = p_id;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

END adress_pkg;
/

-- person_pkg body
CREATE OR REPLACE PACKAGE BODY person_pkg AS
  PROCEDURE new_person(p_first_name      VARCHAR2,
                       p_last_name       VARCHAR2,
                       p_birth_date      DATE,
                       p_ad_country      VARCHAR2,
                       p_ad_city         VARCHAR2,
                       p_ad_street       VARCHAR2,
                       p_ad_house_number VARCHAR2,
                       p_introduction    VARCHAR2) IS
    l_count    NUMBER;
    v_intro_id NUMBER := -1;
  BEGIN
    IF p_first_name IS NOT NULL
       AND p_last_name IS NOT NULL
       AND p_birth_date IS NOT NULL
       AND p_ad_country IS NOT NULL
       AND p_ad_city IS NOT NULL
       AND p_ad_street IS NOT NULL
       AND p_ad_house_number IS NOT NULL
    THEN
    
      SELECT COUNT(*)
        INTO l_count
        FROM person p
       WHERE p.first_name = p_first_name
         AND p.last_name = p_last_name
         AND p.birth_date = p_birth_date;
    
      IF l_count > 0
      THEN
        raise_application_error(-20001, 'The given person is already existing in the database');
      ELSE
      
        adress_pkg.new_adress(p_country => p_ad_country, p_city => p_ad_city, p_street => p_ad_street, p_house_number => p_ad_house_number);
      
        IF p_introduction IS NULL
           OR length(p_introduction) <= 0
        THEN
          INSERT INTO translation
            (LANGUAGE,
             text)
          VALUES
            (100,
             ' ');
        ELSE
          INSERT INTO translation
            (LANGUAGE,
             text)
          VALUES
            (100,
             p_introduction);
        END IF;
      
        v_intro_id := translation_sq.currval;
      
        INSERT INTO person
          (first_name,
           last_name,
           birth_date,
           adress_id,
           introduction)
        VALUES
          (p_first_name,
           p_last_name,
           p_birth_date,
           adress_sq.currval,
           v_intro_id);
      END IF;
    
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE modify_person_data(p_id         NUMBER,
                               p_first_name VARCHAR2,
                               p_last_name  VARCHAR2,
                               p_birth_date DATE) IS
  BEGIN
    IF p_id IS NOT NULL
       AND p_first_name IS NOT NULL
       AND p_last_name IS NOT NULL
       AND p_birth_date IS NOT NULL
    THEN
    
      UPDATE person p
         SET p.first_name = p_first_name,
             p.last_name  = p_last_name,
             p.birth_date = p_birth_date
       WHERE p.id = p_id;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE modify_introduction(p_id           NUMBER,
                                p_introduction VARCHAR2) IS
  BEGIN
    IF p_id IS NOT NULL
       AND p_introduction IS NOT NULL
    THEN
      UPDATE translation t
         SET t.text = p_introduction
       WHERE t.id = (SELECT p.introduction
                       FROM person p
                      WHERE p.id = p_id);
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE modify_adress(p_id              NUMBER,
                          p_ad_country      VARCHAR2,
                          p_ad_city         VARCHAR2,
                          p_ad_street       VARCHAR2,
                          p_ad_house_number VARCHAR2) IS
    l_adress_id NUMBER;
  BEGIN
    IF p_id IS NOT NULL
       AND p_ad_country IS NOT NULL
       AND p_ad_city IS NOT NULL
       AND p_ad_street IS NOT NULL
       AND p_ad_house_number IS NOT NULL
    THEN
    
      SELECT p.adress_id
        INTO l_adress_id
        FROM person p
       WHERE p.id = p_id;
    
      UPDATE adress a
         SET a.country      = p_ad_country,
             a.city         = p_ad_city,
             a.street       = p_ad_street,
             a.house_number = p_ad_house_number
       WHERE a.id = l_adress_id;
    
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE activate_person(p_id     NUMBER,
                            p_active VARCHAR2) IS
  BEGIN
    IF p_id IS NOT NULL
       AND length(p_active) = 1
    THEN
      UPDATE person p
         SET p.active = p_active
       WHERE p.id = p_id;
    ELSE
      raise_application_error(-20001, 'Problems occured with the given parameters');
    END IF;
  END;
END person_pkg;
/

-- teached_subject_pkg body
CREATE OR REPLACE PACKAGE BODY teached_subject_pkg AS
  PROCEDURE pick_up_subject(p_subject_id  NUMBER,
                            p_teacher_id  NUMBER,
                            p_description VARCHAR2) IS
    l_count NUMBER;
  BEGIN
    IF p_subject_id IS NOT NULL
       AND p_teacher_id IS NOT NULL
       AND p_description IS NOT NULL
    THEN
    
      SELECT COUNT(*)
        INTO l_count
        FROM teached_subject ts
       WHERE ts.subject_id = p_subject_id
         AND ts.teacher_id = p_teacher_id;
    
      IF l_count > 0
      THEN
        raise_application_error(-20001, 'The given person has already teaching this subject');
      ELSE
      
        INSERT INTO translation
          (LANGUAGE,
           text)
        VALUES
          (100,
           p_description);
        INSERT INTO teached_subject
          (subject_id,
           teacher_id,
           description)
        VALUES
          (p_subject_id,
           p_teacher_id,
           translation_sq.currval);
      END IF;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE activate_subject(p_subject_id NUMBER,
                             p_teacher_id NUMBER,
                             p_active     VARCHAR2) IS
  BEGIN
    IF p_subject_id IS NOT NULL
       AND p_teacher_id IS NOT NULL
       AND p_active IS NOT NULL
    THEN
      UPDATE teached_subject ts
         SET ts.active = p_active
       WHERE ts.subject_id = p_subject_id
         AND ts.teacher_id = p_teacher_id;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

  PROCEDURE modify_subject_description(p_subject_id  NUMBER,
                                       p_teacher_id  NUMBER,
                                       p_description VARCHAR2) IS
  BEGIN
    IF p_subject_id IS NOT NULL
       AND p_teacher_id IS NOT NULL
       AND p_description IS NOT NULL
    THEN
      UPDATE translation t
         SET t.text = p_description
       WHERE t.id = (SELECT ts.description
                       FROM teached_subject ts
                      WHERE ts.subject_id = p_subject_id
                        AND ts.teacher_id = p_teacher_id);
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;
END teached_subject_pkg;
/

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
  join adress a
    on p.adress_id = a.id
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
        JOIN adress a
          ON p.adress_id = a.id
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
