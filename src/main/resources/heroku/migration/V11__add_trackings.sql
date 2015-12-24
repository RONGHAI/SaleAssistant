



CREATE TABLE trackings
(
  id serial NOT NULL,
  carrier_id integer NOT NULL,
  package_type text NOT NULL,
  package_number text NOT NULL,
  disabled smallint NOT NULL DEFAULT (0)::smallint,
  add_time timestamp without time zone,
  update_time timestamp without time zone,
  note text,
  CONSTRAINT trackings_pkey PRIMARY KEY (id)
);
-- CREATE SEQUENCE trackings_id_seq;
ALTER TABLE trackings ALTER COLUMN id SET DEFAULT nextval('trackings_id_seq');
ALTER TABLE trackings ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE trackings_id_seq OWNED BY trackings.id;    -- 8.2 or later

 
