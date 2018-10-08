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
