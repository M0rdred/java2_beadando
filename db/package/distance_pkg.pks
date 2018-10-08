-- distance_pkg head
CREATE OR REPLACE PACKAGE distance_pkg AS
  FUNCTION get_distance(p_city_from IN VARCHAR2,
                        p_city_to   IN VARCHAR2) RETURN NUMBER;

  PROCEDURE update_distances;
END distance_pkg;
/
