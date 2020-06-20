
basketuat
basket
basketuat

ELASTIC CLOUD
https://55a67ecbd7984d4b9f344e8ee07711f9.us-east-1.aws.found.io:9243/
elastic
mUScqIS3rHNFsDFmgwqhJ8Hp

BONSAI

https://app.bonsai.io/clusters/basket-bonsai-uat-4797063309?hsmd=tail&hswd=900000&hstw=86400000

https://55a67ecbd7984d4b9f344e8ee07711f9.us-east-1.aws.found.io:9243/


https://bdb3x0k54n:ywhzsezt3c@basket-bonsai-uat-4797063309.ap-southeast-2.bonsaisearch.net:443
bdb3x0k54n
ywhzsezt3c

git clone https://KunalPimparkhede@bitbucket.org/KunalPimparkhede/basket.git

basketpwd
NAHBNbBfZPGMTaYquXD2


CREATE KEYSPACE basket WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

create table batchrequest ( user_name text , id UUID , subject_name text, category text, requirement text , primary key(user_name,id))


    CREATE MATERIALIZED VIEW batchrequest_id_un_ct AS SELECT user_name  , id  , subject_name , category , requirement  FROM batchrequest where id IS NOT NULL and category IS NOT NULL PRIMARY KEY (id,user_name,category);

    CREATE MATERIALIZED VIEW batchrequest_ct_id_un AS SELECT user_name  , id  , subject_name , category , requirement  FROM batchrequest where id IS NOT NULL and category IS NOT NULL PRIMARY KEY (category,id,user_name);


create table basketuser
(
   name text,
     user_type text,
category text,
   subject text,
 email_id text,
     mobile text,
 free_text_requirement text,
   mode text,
  password text,
    confirmpassword text,
    teaching_experience text,
industrial_experience text,
    institute text,
    location text,
    primary key(name,user_type)
)

alter table basketuser ADD  enabled boolean;
alter table basketuser ADD  ui_password text;
alter table basketuser ADD  vertoken text;
alter table basketuser ADD  created date;
alter table basketuser ADD  updated date;

//add created and last updated in each table


insert into basket.basketuser
(name,user_type,category,confirmpassword,email_id,free_text_requirement,industrial_experience,institute,location,mobile,mode,password,subject,teaching_experience)
values('boo','TEACHER','ENGINEERING','boo','boo','boo','wds','wew','weqeqwe','www','ww','boo','boo','boo');


create table otpuser
(
   name text,
   created    date,
   otp_key    text,
    primary key(name)
)