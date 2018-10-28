CREATE OR REPLACE PACKAGE BODY distance_pkg AS

  c_google_maps_url   CONSTANT VARCHAR2(255) := 'google_distance_matrix_url';
  c_response_format   CONSTANT VARCHAR2(255) := 'distance_response_format';
  c_origins_param     CONSTANT VARCHAR2(255) := 'origins=';
  c_destination_param CONSTANT VARCHAR2(255) := 'destinations=';
  c_api_key_param     CONSTANT VARCHAR2(255) := 'key=';
  c_api_key           CONSTANT VARCHAR2(255) := 'api_key';
  c_response_status   CONSTANT VARCHAR2(255) := '/DistanceMatrixResponse/row/element/status';
  c_response_value    CONSTANT VARCHAR2(255) := '/DistanceMatrixResponse/row/element/distance/value';

  FUNCTION distance_from_google_maps_api(p_city_from IN VARCHAR2
                                        ,p_city_to   IN VARCHAR2)
    RETURN NUMBER IS
    v_distance        NUMBER;
    v_status          VARCHAR2(100);
    v_url             VARCHAR2(255);
    v_response_format VARCHAR2(255);
    v_api_key         VARCHAR2(255);
    v_value           CLOB;
  BEGIN
  
    SELECT p.param_value
      INTO v_url
      FROM application_parameters p
     WHERE p.param_name = c_google_maps_url;
  
    SELECT p.param_value
      INTO v_response_format
      FROM application_parameters p
     WHERE p.param_name = c_response_format;
  
    SELECT p.param_value
      INTO v_api_key
      FROM application_parameters p
     WHERE p.param_name = c_api_key;
  
    dbms_output.put_line(v_url || v_response_format ||
                         -- ?
                         chr(63) || c_origins_param || p_city_from ||
                         -- &
                         chr(38) || c_destination_param || p_city_to ||
                         -- &
                         chr(38) || c_api_key_param || v_api_key || '\n');
  
    -- NoFormat Start
      INSERT INTO temp
        SELECT httpuritype(
          v_url ||
          v_response_format ||
          -- ?
          chr(63) ||
          c_origins_param ||          
          p_city_from ||
          -- &
          chr(38) ||
          c_destination_param ||
          p_city_to ||
          -- &
          chr(38) ||
          c_api_key_param ||
          v_api_key
          )
        .getclob()
          FROM dual;
    -- NoFormat End
  
    SELECT text INTO v_value FROM temp;
    dbms_output.put_line(v_value);
  
    SELECT extractvalue(xmltype(text), c_response_status)
      INTO v_status
      FROM temp;
  
    IF v_status IS NOT NULL
       AND v_status = 'OK'
    THEN
    
      SELECT extractvalue(xmltype(text), c_response_value)
        INTO v_distance
        FROM temp;
    
      IF v_distance IS NOT NULL
      THEN
        INSERT INTO city_distance
          (city_from
          ,city_to
          ,distance
          ,last_mod)
        VALUES
          (p_city_from
          ,p_city_to
          ,v_distance
          ,SYSDATE);
      END IF;
      COMMIT;
    
    END IF;
  
    RETURN v_distance;
  END;

  FUNCTION get_distance(p_city_from IN VARCHAR2
                       ,p_city_to   IN VARCHAR2) RETURN NUMBER IS
    v_distance NUMBER;
  BEGIN
  
    IF p_city_from IS NOT NULL
       AND p_city_to IS NOT NULL
    THEN
    
      IF p_city_from = p_city_to
      THEN
        RETURN 0;
      END IF;
    
      FOR c IN (SELECT cd.city_from
                      ,cd.city_to
                      ,cd.distance
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
        FOR c IN (SELECT cd.city_from
                        ,cd.city_to
                        ,cd.distance
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
    v_distance        NUMBER;
    v_status          VARCHAR2(100);
    v_url             VARCHAR2(255);
    v_response_format VARCHAR2(255);
    v_api_key         VARCHAR2(255);
  BEGIN
  
    SELECT p.param_value
      INTO v_url
      FROM application_parameters p
     WHERE p.param_name = c_google_maps_url;
  
    SELECT p.param_value
      INTO v_response_format
      FROM application_parameters p
     WHERE p.param_name = c_response_format;
  
    SELECT p.param_value
      INTO v_api_key
      FROM application_parameters p
     WHERE p.param_name = c_api_key;
  
    FOR upd IN (SELECT c.*
                  FROM city_distance c
                 WHERE trunc(SYSDATE) - trunc(c.last_mod) > 10)
    LOOP
        -- NoFormat Start
        INSERT INTO temp
          SELECT httpuritype(
            v_url ||
            v_response_format ||
            -- ?
            chr(63) ||
            c_origins_param ||
            upd.city_from ||
            -- &
            chr(38) ||
            c_destination_param ||
            upd.city_to ||
            -- &
            chr(38) ||
            c_api_key_param ||
            v_api_key
            )
          .getclob()
            FROM dual;
      -- NoFormat End
    
      SELECT extractvalue(xmltype(text), c_response_status)
        INTO v_status
        FROM temp;
    
      IF v_status IS NOT NULL
         AND v_status = 'OK'
      THEN
      
        SELECT extractvalue(xmltype(text), c_response_value)
          INTO v_distance
          FROM temp;
      
        IF v_distance IS NOT NULL
        THEN
          UPDATE city_distance
             SET distance = v_distance
                ,last_mod = SYSDATE
           WHERE city_from = upd.city_from
             AND city_to = upd.city_to;
        END IF;
      
      END IF;
      COMMIT;
    
    END LOOP;
  
  END;

END distance_pkg;
/
