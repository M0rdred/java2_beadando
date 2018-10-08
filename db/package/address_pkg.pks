-- address_pkg head
CREATE OR REPLACE PACKAGE address_pkg AS
  PROCEDURE new_address(p_country      VARCHAR2,
                       p_city         VARCHAR2,
                       p_street       VARCHAR2,
                       p_house_number VARCHAR2);
  PROCEDURE modify_address(p_id           NUMBER,
                          p_country      VARCHAR2,
                          p_city         VARCHAR2,
                          p_street       VARCHAR2,
                          p_house_number VARCHAR2);
END address_pkg;
/
