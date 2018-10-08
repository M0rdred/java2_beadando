--------------------- TEST Tutor_DB_Project ---------------------

------------------------- Felhaszn�l�k --------------------------
-- �j ember regisztr�l�sa
BEGIN
  person_pkg.new_person(p_first_name      => 'Jakab',
                        p_last_name       => 'Gipsz',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Debrecen',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '14',
                        p_introduction    => '�n vagyok a Gipsz Jakab');
END;

-- ugyanazon a n�vvel �s sz�let�si d�tummal nem regisztr�lhat m�s --> exception
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

-- maximum a bemutatkoz�s hi�nyozhat, a t�bbi adatot k�telez� megadni
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

-- de a bemutatkoz�s hi�nyozhat
BEGIN
  person_pkg.new_person(p_first_name      => 'John',
                        p_last_name       => 'Smith',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Sikl�s',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => '');
END;

-- user bemutatkoz�s�nak m�dos�t�sa
BEGIN
  person_pkg.modify_introduction(p_id           => 101,
                                 p_introduction => 'The name is Smith. John Smith.');
END;

-- tov�bbi userek felvitele
BEGIN
  person_pkg.new_person(p_first_name      => 'Tony',
                        p_last_name       => 'Stark',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'S�sd',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => 'I am Ironman');
  person_pkg.new_person(p_first_name      => 'Horv�th',
                        p_last_name       => 'G�za',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Kecskem�t',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => '30 �ves gimn�ziumi oktat�si gyakorlatom van');
  person_pkg.new_person(p_first_name      => 'Senior',
                        p_last_name       => 'Developer',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Ny�regyh�za',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => 'Nagyon szeretek Java-t tan�tani');
  person_pkg.new_person(p_first_name      => 'T�th',
                        p_last_name       => 'J�nos',
                        p_birth_date      => to_date('1988.12.10',
                                                     'YYYY.MM.DD'),
                        p_ad_country      => 'Hungary',
                        p_ad_city         => 'Sopron',
                        p_ad_street       => 'Kossuth u.',
                        p_ad_house_number => '30',
                        p_introduction    => 'Irodalm�r szakon v�geztem');
END;

-- user lakc�m�nek m�dos�t�sa
BEGIN
  person_pkg.modify_address(p_id              => 100,
                           p_ad_country      => 'Hungary',
                           p_ad_city         => 'Szeged',
                           p_ad_street       => 'Kossuth u.',
                           p_ad_house_number => '30');
END;

-- user adatainak m�dos�t�sa
BEGIN
  person_pkg.modify_person_data(p_id         => 100,
                                p_first_name => '�kos',
                                p_last_name  => 'Kov�cs',
                                p_birth_date => to_date('1980.10.11',
                                                        'YYYY.MM.DD'));
END;

-- user aktiv�l�sa/deaktiv�l�sa
BEGIN
  person_pkg.activate_person(p_id => 100, p_active => 'Y');
  person_pkg.activate_person(p_id => 101, p_active => 'Y');
  person_pkg.activate_person(p_id => 102, p_active => 'Y');
  person_pkg.activate_person(p_id => 103, p_active => 'Y');
  person_pkg.activate_person(p_id => 104, p_active => 'Y');
END;

-------------------------- Tant�rgyak ---------------------------
-- �j tant�rgy felvitele
BEGIN
  subject_pkg.new_subject(p_name        => 'matek',
                          p_description => 'Matematika le�r�sa');
END;

-- egy tant�rgyat csak egyszer lehet felvinni
BEGIN
  subject_pkg.new_subject(p_name => 'matek', p_description => 'Matematika');
END;

-- tov�bbi tant�rgyak felvitele
BEGIN
  subject_pkg.new_subject(p_name        => 'teol�gia',
                          p_description => 'Teol�gia le�r�sa');
  subject_pkg.new_subject(p_name        => 'irodalom',
                          p_description => 'Irodalom le�r�sa');
  subject_pkg.new_subject(p_name        => 'Java programoz�s',
                          p_description => 'Java programoz�s le�r�sa');
  subject_pkg.new_subject(p_name        => 'atomfizika',
                          p_description => 'Atomfizika le�r�sa');
  subject_pkg.new_subject(p_name        => 'filoz�fia',
                          p_description => 'Filoz�fia le�r�sa');
END;

-- tant�rgy m�dos�t�sa
BEGIN
  subject_pkg.modify_subject(p_id          => 100,
                             p_active      => 'Y',
                             p_description => 'Matematika r�vid le�r�sa');
  subject_pkg.modify_subject(p_id          => 101,
                             p_active      => 'Y',
                             p_description => 'Teol�gia r�vid le�r�sa');
  subject_pkg.modify_subject(p_id          => 102,
                             p_active      => 'Y',
                             p_description => 'Irodalom r�vid le�r�sa');
  subject_pkg.modify_subject(p_id          => 103,
                             p_active      => 'Y',
                             p_description => 'Java programoz�s r�vid le�r�sa');
  subject_pkg.modify_subject(p_id          => 104,
                             p_active      => 'Y',
                             p_description => 'Atomfizika r�vid le�r�sa');
  subject_pkg.modify_subject(p_id          => 105,
                             p_active      => 'Y',
                             p_description => 'Filoz�fia r�vid le�r�sa');
END;

-- tant�rgy t�rl�se(deaktiv�l�sa)
BEGIN
  subject_pkg.delete_subject(p_id => 101);
END;

---------------------- Tant�rgyak okat�sa -----------------------
-- tant�rgy oktat�sa
BEGIN
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 100,
                                      p_description => 'Kov�cs �kos matematik�t oktat');
END;

-- egy valakihez egy tant�rgyat csak egyszer lehet felvinni
BEGIN
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 100,
                                      p_description => 'matematika oktat�s');
END;

-- tov�bbi tant�rgyak oktat�s�nak megkezd�se
BEGIN
  teached_subject_pkg.pick_up_subject(p_subject_id  => 103,
                                      p_teacher_id  => 104,
                                      p_description => '�rtek a Java-hoz!');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 104,
                                      p_teacher_id  => 102,
                                      p_description => 'Barlangban is tudok atomreaktort �p�teni');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 103,
                                      p_teacher_id  => 102,
                                      p_description => 'A Vasember �lt�zet szoftvere nyilv�n Java-n fut...');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 102,
                                      p_teacher_id  => 105,
                                      p_description => '�letem az irodalom sz�ps�ge');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 101,
                                      p_teacher_id  => 101,
                                      p_description => 'Teol�gi�t oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 105,
                                      p_teacher_id  => 101,
                                      p_description => 'Filoz�fi�t oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 101,
                                      p_description => 'Matematik�t oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 100,
                                      p_teacher_id  => 103,
                                      p_description => 'Matematik�t is oktatok');
  teached_subject_pkg.pick_up_subject(p_subject_id  => 102,
                                      p_teacher_id  => 103,
                                      p_description => 'Matematik�t oktatok');
END;

-- t�rgy oktat�s�nak aktiv�l�sa
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

-- oktatott t�rgy szem�lyes le�r�s�nak m�dos�t�sa
BEGIN
  teached_subject_pkg.modify_subject_description(p_subject_id  => 100,
                                                 p_teacher_id  => 100,
                                                 p_description => 'Matekot tan�tok: K.�.');
END;

-- t�vols�gm�r�s Google Matrix API seg�ts�g�vel
-- ha az Adatb�zisban nem tal�l t�rolt �rt�ket akkor fordul csak a Matrix API-hoz(�rtelemszer�en internet kapcsolat sz�ks�ges hozz�)
-- sima SELECT FROM dual eset�n: ORA-14551: cannot perform a DML operation inside a query
DECLARE
  RESULT NUMBER;
BEGIN
  RESULT := distance_pkg.get_distance(p_city_from => 'Szeged',
                                      p_city_to   => 'Debrecen');
  dbms_output.put_line('result: ' || RESULT);
END;

-- k�s�bb m�r Adatb�zisb�l megtal�lja akkor is ha ford�tva adtuk meg neki a v�rosokat
SELECT distance_pkg.get_distance('Debrecen', 'Szeged') FROM dual; -- ehhez m�r nem kell internet

-- keres�s funkci�, az el�z� package-t haszn�lja
/* search_pkg.search( keres� user ID-ja,
            keresett tant�rgy neve, (opcion�lis)
            keresett oktat� neve, (opcion�lis)
            maxim�lis t�vols�g (opcion�lis)
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

-- ha a t�vols�g adatok m�r az Adatb�zisban vannak
SELECT *
  FROM TABLE(search_pkg.search(p_searcher_id  => 103,
                               p_subject_name => NULL,
                               p_teacher_name => NULL,
                               p_max_distance => 1000));

-- aktiv�l�sra v�r� tant�rgyak list�ja
SELECT id, NAME, LANGUAGE, text, active
  FROM subjects_waiting_validation_vw;

-- job kipr�b�l�sa
-- �rjuk �t az egyik sor LAST_MOD �rt�k�t r�gire (pl. egy �vvel kor�bban)
SELECT t.*, t.rowid FROM city_distance t;

-- futtassuk a job-ot manu�lisan
BEGIN
  dbms_scheduler.run_job(job_name => 'refresh_distance_data');
END;
