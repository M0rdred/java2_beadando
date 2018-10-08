-- ty_search_result body
CREATE OR REPLACE TYPE BODY ty_search_result AS
  CONSTRUCTOR FUNCTION ty_search_result RETURN SELF AS RESULT IS
  BEGIN
    RETURN;
  END ty_search_result;
END;
/