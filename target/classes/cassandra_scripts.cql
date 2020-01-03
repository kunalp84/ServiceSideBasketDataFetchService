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


insert into basket.basketuser
(name,user_type,category,confirmpassword,email_id,free_text_requirement,industrial_experience,institute,location,mobile,mode,password,subject,teaching_experience)
values('boo','TEACHER','ENGINEERING','boo','boo','boo','wds','wew','weqeqwe','www','ww','boo','boo','boo');