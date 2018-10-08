#!/bin/bash
DB_USER="tutor"
DB_PASS="tutor"
DB_SERVICE="XE"

sqlplus.exe $DB_USER/$DB_PASS@$DB_SERVICE << EOF
set echo off 
set heading off

@delete_db.sql
commit;
EOF


sqlplus.exe $DB_USER/$DB_PASS@$DB_SERVICE << EOF2
set echo off 
set heading off

@install_db.sql
commit;
EOF2

echo 'Resetting DB done...'
read -p 'Press Enter'
