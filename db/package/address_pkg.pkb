-- address_pkg body
CREATE OR REPLACE PACKAGE BODY address_pkg AS
  PROCEDURE new_address(p_country      VARCHAR2,
                       p_city         VARCHAR2,
                       p_street       VARCHAR2,
                       p_house_number VARCHAR2) IS
    l_address_count NUMBER;
  BEGIN
    IF p_country IS NOT NULL
       AND p_city IS NOT NULL
       AND p_street IS NOT NULL
       AND p_house_number IS NOT NULL
    THEN
    
      SELECT COUNT(*)
        INTO l_address_count
        FROM address a
       WHERE a.country = p_country
         AND a.city = p_city
         AND a.street = p_street
         AND a.house_number = p_house_number;
    
      IF l_address_count > 0
      THEN
        raise_application_error(-20001, 'The given address is already existing in the database');
      ELSE
        INSERT INTO address
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

  PROCEDURE modify_address(p_id           NUMBER,
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
      UPDATE address a
         SET a.country      = p_country,
             a.city         = p_city,
             a.street       = p_street,
             a.house_number = p_house_number
       WHERE a.id = p_id;
    ELSE
      raise_application_error(-20001, 'One or more parameters are empty');
    END IF;
  END;

END address_pkg;
/
