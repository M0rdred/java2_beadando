-- person_pkg head
CREATE OR REPLACE PACKAGE person_pkg AS
  PROCEDURE new_person(p_first_name      VARCHAR2,
                       p_last_name       VARCHAR2,
                       p_birth_date      DATE,
                       p_ad_country      VARCHAR2,
                       p_ad_city         VARCHAR2,
                       p_ad_street       VARCHAR2,
                       p_ad_house_number VARCHAR2,
                       p_introduction    VARCHAR2);
  PROCEDURE modify_person_data(p_id         NUMBER,
                               p_first_name VARCHAR2,
                               p_last_name  VARCHAR2,
                               p_birth_date DATE);
  PROCEDURE modify_introduction(p_id           NUMBER,
                                p_introduction VARCHAR2);
  PROCEDURE modify_address(p_id              NUMBER,
                          p_ad_country      VARCHAR2,
                          p_ad_city         VARCHAR2,
                          p_ad_street       VARCHAR2,
                          p_ad_house_number VARCHAR2);
  PROCEDURE activate_person(p_id     NUMBER,
                            p_active VARCHAR2);
END person_pkg;
/
