prompt Importing table application_parameters...
set feedback off
set define off
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
  
prompt Done.  


  

prompt Importing table person...
set feedback off
set define off
insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Anita', 'Minta', to_date('18-12-1986', 'dd-mm-yyyy'), null, 'Y', 'N', 'Y', null, 'teacher', 'mintaanita', null, 'anita@minta.com', '$2a$10$jph99JMvIKxrG3/XJYttBe8oEg162Yj7pV0Flzh27NtDqodXVPyB2');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Béla', 'Minta', to_date('10-10-2002', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintabela', null, 'bela@minta.com', '$2a$10$h58fKOb/iHokoLs4ar5Mv.bfCfWfG9NNfapkpacRF1QNnSkAExorK');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Cecil', 'Minta', to_date('15-10-2006', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintacecil', null, 'cecil@minta.com', '$2a$10$fToB19akVQ8NIJxK5YjVSujfpN1D1qUVgd8IYpXtw23vP9xNDR01.');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Csaba', 'Minta', to_date('19-05-2000', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintacsaba', null, 'csaba@minta.com', '$2a$10$YxgSQKUzkh8MwYHZAwwlQOfc5irN8eboLmOC0y3Tz3m8/Gp.Vq2JO');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Dénes', 'Minta', to_date('19-10-1990', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintadenes', null, 'denes@minta.hu', '$2a$10$BYAMVpm.l78fVmhW2nMg9.KfDcwZgiqBx8Ytycz6eiFoGwNVQDrlu');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Elek', 'Minta', to_date('01-10-2007', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintaelek', null, 'elek@minta.com', '$2a$10$P8frx8gG6ek4Hu/m1uMyKuSDxuei7ZYQxpgo97VDIqdPwnEp7TG7W');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Ferenc', 'Minta', to_date('30-10-1997', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintaferenc', null, 'ferenc@minta.com', '$2a$10$tT9J74zbnweLNpSenwQr2.6jvTmmuGNW49/ZLw8jMHnhBmyIImYXy');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Géza', 'Minta', to_date('17-06-2004', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintageza', null, 'geza@minta.com', '$2a$10$75qcam55lmqnPAoh.QYIdeKDu9OtxWsfUxflcWb.gPCb6a0BIEykG');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Huba', 'Minta', to_date('06-10-2006', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintahuba', null, 'huba@minta.com', '$2a$10$5Xr/zyfM8G1AkJbwHG.KZ.NKuAvbG//gOnqhXcRA4diA/8RGmeM2W');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Ilona', 'Minta', to_date('19-10-2010', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintailona', null, 'ilona@minta.com', '$2a$10$Kjl4Hz9ZM58A3HvI7cT6t.zKrYO0SggC8feah6up.xnyVLRVUWkay');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('József', 'Minta', to_date('15-10-1992', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintajozsef', null, 'jozsef@minta.com', '$2a$10$xK2OJ90szDR6yf5KLvRo1ehJMiErdNRIgpU8IW28N9LJ1sRs7yMxe');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Kálmán', 'Minta', to_date('12-10-2005', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintakalman', null, 'kalman@minta.com', '$2a$10$Ek2PGyF1.jnSy2F50FqL5u1WU2mNZ.ROD7qNmknUt4gVo7014PFva');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('László', 'Minta', to_date('19-10-1984', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintalaszlo', null, 'laszlo@minta.com', '$2a$10$bj/ODSKpSO.b9sxezMoqyOzQ420k5DrgpwuP8ZJv2.i5ruSE.2Bn.');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Nándor', 'Minta', to_date('18-10-1996', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintanandor', null, 'nandor@minta.com', '$2a$10$C9/iJNtzRsJPItMwnbSCreshpfF6wXfIUV/0/OQTbf9hc8X4K4XhC');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Ottó', 'Minta', to_date('16-02-1995', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintaotto', null, 'otto@minta.com', '$2a$10$KYYf59nzNng/BKZE.awK9uZVEA1FQLI2sDq4P/xysgfLh/NdiQSi2');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Pál', 'Minta', to_date('11-10-2010', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintapal', null, 'pal@minta.com', '$2a$10$QGKO6MImqKRij2Xxq037ruBHhPTw1G.BhhHzCyXGIJpLRzPs3Z54C');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Róbert', 'Minta', to_date('28-10-1995', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintarobert', null, 'robert@minta.com', '$2a$10$BPFbJrEfuG60E02QLt1weOlzIlkIPczqyUxOmtr.wjnfJ6H8fSsuG');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Sándor', 'Minta', to_date('09-10-2009', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintasandor', null, 'sandor@minta.com', '$2a$10$qaw0/DHsIhm9O3VvDo9TO.6JaP3xgrCgn0wjYpYV94/mvp.xgKYom');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Tamás', 'Minta', to_date('13-10-1994', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintatamas', null, 'tamas@minta.com', '$2a$10$nPoNQ1jDbUJy.aop7NP1VeNbpI66jJbDo0xzpEJdxGyUbHscuiZlq');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Ubul', 'Minta', to_date('20-10-2001', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintaubul', null, 'ubul@minta.com', '$2a$10$NLmjNMRuFzBMG6Qkc8S9QO8kc0ZA7mpBa3HzvUBSOyg3V7W8/HTIC');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Vendel', 'Minta', to_date('09-10-1998', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintavendel', null, 'vendel@minta.com', '$2a$10$.At4v9bWivohcBuk5rm9duTxsgitEWTLbyWcLyVXOcwHTYrpeyzUi');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Xénia', 'Minta', to_date('06-01-2005', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintaxenia', null, 'xenia@minta.com', '$2a$10$TSralIYAT7rqfRzgGG1ng.TZfp/SYbKx8S3nCJo9uGYlgMz1y3pTu');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Zoltán', 'Minta', to_date('12-06-1996', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintazoltan', null, 'zoltan@minta.com', '$2a$10$/ct72jX4oMvqDF4oBUpWV.cWdGoIn3ZQqbojsAofi.PA8mOmNQgna');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Zsolt', 'Minta', to_date('17-12-1993', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintazsolt', null, 'zsolt@minta.com', '$2a$10$spr62BrZStM.a4guVrv/keEAIgAx1JybkyZUyVGJTTHIRlAkNP9bm');

insert into person (FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD)
values ('Ödön', 'Minta', to_date('19-10-2002', 'dd-mm-yyyy'), null, 'N', 'N', 'N', null, 'user', 'mintaodon', null, 'odon@minta.com', '$2a$10$JMJA2I3yMApkkOFcPh6vXek917BqT0ynrOCfrISqG5nOt4r2g3vD2');

insert into person (ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, ADDRESS_ID, IS_TEACHER, IS_ADMIN, IS_ACTIVE, INTRODUCTION, ROLE, USER_NAME, PHONE, EMAIL, PASSWORD, MOD_USER, CREATED_ON, LAST_MOD, DML_FLAG, VERSION)
values (125, 'Admin', 'Minta', to_date('28-12-2004', 'dd-mm-yyyy'), null, 'N', 'Y', 'Y', null, 'admin', 'admin', null, 'admin@tutor.hu', '$2a$10$JP62uUAN8fdFyCHsgfCfb.5iy.DNxByE8FOEi7VutkEzYSWPz0QZe', 'TUTOR', '18-OKT.  -30 19.47.54,000000', '18-OKT.  -30 19.47.54,000000', 'I', 1);


prompt Done.
