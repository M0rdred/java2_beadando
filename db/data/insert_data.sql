INSERT INTO application_parameters
  (param_name
  ,param_value)
VALUES
  ('google_distance_matrix_url'
  ,'https://maps.googleapis.com/maps/api/distancematrix/');

INSERT INTO application_parameters
  (param_name
  ,param_value)
VALUES
  ('distance_response_format'
  ,'xml');

INSERT INTO application_parameters
  (param_name
  ,param_value)
VALUES
  ('api_key'
  ,'AIzaSyD6M1napXy0jBNAbRF_-7DlW3QBDq4iHPE');

INSERT INTO LANGUAGE
  (id,
   code,
   NAME)
VALUES
  (language_sq.nextval,
   'hu',
   'magyar');
