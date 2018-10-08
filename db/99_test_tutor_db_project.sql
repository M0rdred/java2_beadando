--------------------- TEST Tutor_DB_Project ---------------------

------------------------- Felhasználók --------------------------
-- új ember regisztrálása
BEGIN
  person_pkg.new_person(p_first_name      => 'Jakab',
                        p_last_name       => 'Gipsz',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Debrecen',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '14',
                        p_introduction    => 'Én vagyok a Gipsz Jakab');
END;

-- ugyanazon a névvel és születési dátummal nem regisztrálhat más --> exception
BEGIN
  person_pkg.new_person(p_first_name      => 'Jakab',
                        p_last_name       => 'Gipsz',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Debrecen',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '14',
                        p_introduction    => 'Gipsz Jakab vagyok');
END;

-- maximum a bemutatkozás hiányozhat, a többi adatot kötelezõ megadni
BEGIN
  person_pkg.new_person(p_first_name      => '',
                        p_last_name       => '',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Debrecen',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '14',
                        p_introduction    => '');
END;

-- de a bemutatkozás hiányozhat
BEGIN
  person_pkg.new_person(p_first_name      => 'John',
                        p_last_name       => 'Smith',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Siklós',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => '');
END;

-- user bemutatkozásának módosítása
BEGIN
  person_pkg.modify_introduction(p_id           => 101,
                                 p_introduction => 'The name is Smith. John Smith.');
END;

-- további userek felvitele
BEGIN
  person_pkg.new_person(p_first_name      => 'Tony',
                        p_last_name       => 'Stark',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Sásd',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => 'I am Ironman');
  person_pkg.new_person(p_first_name      => 'Horváth',
                        p_last_name       => 'Géza',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Kecskemét',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => '30 éves gimnáziumi oktatási gyakorlatom van');
  person_pkg.new_person(p_first_name      => 'Senior',
                        p_last_name       => 'Developer',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Nyíregyháza',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => 'Nagyon szeretek Java-t tanítani');
  person_pkg.new_person(p_first_name      => 'Tóth',
                        p_last_name       => 'János',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Sopron',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => 'Irodalmár szakon végeztem');
END;

-- user lakcímének módosítása
BEGIN
  person_pkg.modify_address(p_id              => 100,
                           p_ad_country      => 'Hungary',
                           p_ad_city         => 'Szeged',
                           p_ad_street       => 'Kossuth u.',
                           p_ad_house_number => '30');
END;

-- user adatainak módosítása
BEGIN
  person_pkg.modify_person_data(p_id         => 100,
                                p_first_name => 'Ákos',
                                p_last_name  => 'Kovács',
                                p_birth_date => to_date('1980.10.11',
                                                        'YYYY.MM.DD'));
END;

-- user aktiválása/deaktiválása
BEGIN
  person_pkg.activate_person(p_id => 100, p_active => 'Y');
  person_pkg.activate_person(p_id => 101, p_active => 'Y');
  person_pkg.activate_person(p_id => 102, p_active => 'Y');
  person_pkg.activate_person(p_id => 103, p_active => 'Y');
  person_pkg.activate_person(p_id => 104, p_active => 'Y');
END;

-------------------------- Tantárgyak ---------------------------
-- új tantárgy felvitele
BEGIN
  subject_pkg.new_subject(p_name        => 'matek',
                          p_description => 'Matematika leírása');
END;

-- egy tantárgyat csak egyszer lehet felvinni
BEGIN
  subject_pkg.new_subject(p_name => 'matek', p_description => 'Matematika');
END;

-- további tantárgyak felvitele
BEGIN
  subject_pkg.new_subject(p_name        => 'teológia',
                          p_description => 'Teológia leírása');
  subject_pkg.new_subject(p_name        => 'irodalom',
                          p_description => 'Irodalom leírása');
  subject_pkg.new_subject(p_name        => 'Java programozás',
                          p_description => 'Java programozás leírása');
  subject_pkg.new_subject(p_name        => 'atomfizika',
                          p_description => 'Atomfizika leírása');
  subject_pkg.new_subject(p_name        => 'filozófia',
                          p_description => 'Filozófia leírása');
END;

-- tantárgy módosítása
BEGIN
  subject_pkg.modify_subject(p_id          => 100,
                             p_active      => 'Y',
                             p_description => 'Matematika rövid leírása');
  subject_pkg.modify_subject(p_id          => 101,
                             p_active      => 'Y',
                             p_description => 'Teológia rövid leírása');
  subject_pkg.modify_subject(p_id          => 102,
                             p_active      => 'Y',
                             p_description => 'Irodalom rövid leírása');
  subject_pkg.modify_subject(p_id          => 103,
                             p_active      => 'Y',
                             p_description => 'Java programozás rövid leírása');
  subject_pkg.modify_subject(p_id          => 104,
                             p_active      => 'Y',
                             p_description => 'Atomfizika rövid leírása');
  subject_pkg.modify_subject(p_id          => 105,
                             p_active      => 'Y',
                             p_description => 'Filozófia rövid leírása');
END;

-- tantárgy törlése(deaktiválása)
BEGIN
  subject_pkg.delete_subject(p_id => 101);
END;

---------------------- Tantárgyak okatása -----------------------
-- tantárgy oktatása
BEGIN
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 100,
                                      p_description => 'Kovács Ákos matematikát oktat');
END;

-- egy valakihez egy tantárgyat csak egyszer lehet felvinni
BEGIN
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 100,
                                      p_description => 'matematika oktatás');
END;

-- további tantárgyak oktatásának megkezdése
BEGIN
  teached_subject_pkg.pick_up_subject(p_subject_id  => 103,
                                      p_teacher_id  => 104,
                                      p_description => 'Értek a Java-hoz!');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 104,
                                      p_teacher_id  => 102,
                                      p_description => 'Barlangban is tudok atomreaktort építeni');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 103,
                                      p_teacher_id  => 102,
                                      p_description => 'A Vasember öltözet szoftvere nyilván Java-n fut...');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 102,
                                      p_teacher_id  => 105,
                                      p_description => 'Életem az irodalom szépsége');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 101,
                                      p_teacher_id  => 101,
                                      p_description => 'Teológiát oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 105,
                                      p_teacher_id  => 101,
                                      p_description => 'Filozófiát oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 101,
                                      p_description => 'Matematikát oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 103,
                                      p_description => 'Matematikát is oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 102,
                                      p_teacher_id  => 103,
                                      p_description => 'Matematikát oktatok');
END;

-- tárgy oktatásának aktiválása
BEGIN
  teached_subject_pkg.activate_subject(p_subject_id => 100,
                                       p_teacher_id => 100,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 103,
                                       p_teacher_id => 104,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 104,
                                       p_teacher_id => 102,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 103,
                                       p_teacher_id => 102,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 102,
                                       p_teacher_id => 105,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 101,
                                       p_teacher_id => 101,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 105,
                                       p_teacher_id => 101,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 100,
                                       p_teacher_id => 101,
                                       p_active     => 'Y');
  teached_subject_pkg.activate_subject(p_subject_id => 102,
                                       p_teacher_id => 103,
                                       p_active     => 'Y');
END;

-- oktatott tárgy személyes leírásának módosítása
BEGIN
  teached_subject_pkg.modify_subject_description(p_subject_id  => 100,
                                                 p_teacher_id  => 100,
                                                 p_description => 'Matekot tanítok: K.Á.');
END;

-- távolságmérés Google Matrix API segítségével
-- ha az Adatbázisban nem talál tárolt értéket akkor fordul csak a Matrix API-hoz(értelemszerûen internet kapcsolat szükséges hozzá)
-- sima SELECT FROM dual esetén: ORA-14551: cannot perform a DML operation inside a query
DECLARE
  RESULT NUMBER;
BEGIN
  RESULT := distance_pkg.get_distance(p_city_from => 'Szeged',
                                      p_city_to   => 'Debrecen');
  dbms_output.put_line('result: ' || RESULT);
END;

-- késõbb már Adatbázisból megtalálja akkor is ha fordítva adtuk meg neki a városokat
SELECT distance_pkg.get_distance('Debrecen', 'Szeged') FROM dual; -- ehhez már nem kell internet

-- keresés funkció, az elõzõ package-t használja
/* search_pkg.search( keresõ user ID-ja,
            keresett tantárgy neve, (opcionális)
            keresett oktató neve, (opcionális)
            maximális távolság (opcionális)
);
*/
DECLARE
  RESULT ty_search_result_table;
BEGIN
  RESULT := search_pkg.search(p_searcher_id  => 103,
                              p_subject_name => NULL,
                              p_teacher_name => NULL,
                              p_max_distance => 1000);

  FOR i IN result.first .. result.last LOOP
    dbms_output.put_line(RESULT(i)
                         .first_name || ', ' || RESULT(i).last_name || ', ' || RESULT(i)
                         .country || ', ' || RESULT(i).city || ', ' || RESULT(i)
                         .street || ', ' || RESULT(i).house_number || ', ' || RESULT(i)
                         .introduction || ', ' || RESULT(i).subject_name || ', ' || RESULT(i)
                         .subject_description || ', ' || RESULT(i)
                         .personal_subject_description || ', ' || RESULT(i)
                         .distance);
  END LOOP;
END;

-- ha a távolság adatok már az Adatbázisban vannak
SELECT *
  FROM TABLE(search_pkg.search(p_searcher_id  => 103,
                               p_subject_name => NULL,
                               p_teacher_name => NULL,
                               p_max_distance => 1000));

-- aktiválásra váró tantárgyak listája
SELECT id, NAME, LANGUAGE, text, active
  FROM subjects_waiting_validation_vw;

-- job kipróbálása
-- írjuk át az egyik sor LAST_MOD értékét régire (pl. egy évvel korábban)
SELECT t.*, t.rowid FROM city_distance t;

-- futtassuk a job-ot manuálisan
BEGIN
  dbms_scheduler.run_job(job_name => 'refresh_distance_data');
END;
