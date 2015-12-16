



CREATE TABLE users
(
  id serial NOT NULL,
  name text NOT NULL,
  email text NOT NULL,
  password text NOT NULL,
  secret_token character varying(20) NOT NULL DEFAULT ''::character varying,
  disabled smallint NOT NULL DEFAULT (0)::smallint,
  add_time timestamp without time zone,
  update_time timestamp without time zone,
  note text,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);
DROP SEQUENCE  IF EXISTS users_id_seq;
CREATE SEQUENCE users_id_seq;
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq');
ALTER TABLE users ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE users_id_seq OWNED BY users.id;    -- 8.2 or later

 