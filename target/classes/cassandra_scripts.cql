CREATE KEYSPACE basket WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

create table batchrequest ( user_name text , id UUID , subject_name text, category text, requirement text , primary key(user_name,id))


    CREATE MATERIALIZED VIEW batchrequest_id_un_ct AS SELECT user_name  , id  , subject_name , category , requirement  FROM batchrequest where id IS NOT NULL and category IS NOT NULL PRIMARY KEY (id,user_name,category);

    CREATE MATERIALIZED VIEW batchrequest_ct_id_un AS SELECT user_name  , id  , subject_name , category , requirement  FROM batchrequest where id IS NOT NULL and category IS NOT NULL PRIMARY KEY (category,id,user_name);
