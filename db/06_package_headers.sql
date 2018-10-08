-- distance_pkg head
CREATE OR REPLACE PACKAGE distance_pkg AS
  FUNCTION get_distance(p_city_from IN VARCHAR2,
                        p_city_to   IN VARCHAR2) RETURN NUMBER;

  PROCEDURE update_distances;
END distance_pkg;
/

-- subject_pkg head
CREATE OR REPLACE PACKAGE subject_pkg IS
  PROCEDURE new_subject(p_name        VARCHAR2,
                        p_description VARCHAR2);
  PROCEDURE modify_subject(p_id          NUMBER,
                           p_active      VARCHAR2,
                           p_description VARCHAR2);
  PROCEDURE delete_subject(p_id NUMBER);

END subject_pkg;
/

-- adress_pkg head
CREATE OR REPLACE PACKAGE adress_pkg AS
  PROCEDURE new_adress(p_country      VARCHAR2,
                       p_city         VARCHAR2,
                       p_street       VARCHAR2,
                       p_house_number VARCHAR2);
  PROCEDURE modify_adress(p_id           NUMBER,
                          p_country      VARCHAR2,
                          p_city         VARCHAR2,
                          p_street       VARCHAR2,
                          p_house_number VARCHAR2);
END adress_pkg;
/

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
  PROCEDURE modify_adress(p_id              NUMBER,
                          p_ad_country      VARCHAR2,
                          p_ad_city         VARCHAR2,
                          p_ad_street       VARCHAR2,
                          p_ad_house_number VARCHAR2);
  PROCEDURE activate_person(p_id     NUMBER,
                            p_active VARCHAR2);
END person_pkg;
/

-- teached_subject_pkg head
CREATE OR REPLACE PACKAGE teached_subject_pkg AS
  PROCEDURE pick_up_subject(p_subject_id  NUMBER,
                            p_teacher_id  NUMBER,
                            p_description VARCHAR2);
  PROCEDURE activate_subject(p_subject_id NUMBER,
                             p_teacher_id NUMBER,
                             p_active     VARCHAR2);
  PROCEDURE modify_subject_description(p_subject_id  NUMBER,
                                       p_teacher_id  NUMBER,
                                       p_description VARCHAR2);
END teached_subject_pkg;
/

-- search_pkg head
CREATE OR REPLACE PACKAGE search_pkg AS
  FUNCTION search(p_searcher_id  NUMBER,
                  p_subject_name VARCHAR2,
                  p_teacher_name VARCHAR2,
                  p_max_distance NUMBER) RETURN ty_search_result_table;
END search_pkg;
/
