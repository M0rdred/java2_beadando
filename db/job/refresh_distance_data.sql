BEGIN
  dbms_scheduler.create_job(job_name        => 'refresh_distance_data',
                            job_type        => 'STORED_PROCEDURE',
                            job_action      => 'distance_pkg.update_distances',
                            start_date      => to_date('2018-01-01 00:00:00',
                                                       'yyyy-mm-dd hh24:mi:ss'),
                            repeat_interval => 'FREQ=DAILY',
                            comments        => 'Refreshing old distance data');
END;
/
