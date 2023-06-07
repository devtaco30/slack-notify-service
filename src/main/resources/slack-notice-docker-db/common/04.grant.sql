alter user sntapp set search_path to snt;
grant all on schema snts to sntapp;
grant all privileges on all tables in schema snt to sntapp;
grant UPDATE, USAGE, SELECT ON ALL sequences in schema snt to sntapp;