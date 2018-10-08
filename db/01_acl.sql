DECLARE
  cn_acl CONSTANT VARCHAR2(100) := 'googleapis.xml';
BEGIN
  dbms_network_acl_admin.create_acl(
         acl => cn_acl, 
         description => 'Google Translate API', 
         principal => 'TUTOR', 
         is_grant => TRUE, 
         privilege => 'connect', 
         start_date => NULL, 
         end_date => NULL
         );

  dbms_network_acl_admin.add_privilege(
         acl => cn_acl, 
         principal => 'TUTOR', 
         is_grant => TRUE, 
         privilege => 'resolve'
         );

  dbms_network_acl_admin.assign_acl(
         acl => cn_acl, 
         host => 'ajax.googleapis.com'
         );

  dbms_network_acl_admin.assign_acl(
         acl => cn_acl, 
         host => 'maps.googleapis.com'
         );
END;
/
