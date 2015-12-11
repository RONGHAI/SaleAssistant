

CREATE TABLE user_navigations (
    id integer NOT NULL,
    user_id integer NOT NULL,
    navigation_id integer NOT NULL,
    disabled smallint DEFAULT 0::smallint NOT NULL,
    add_time timestamp without time zone,
    update_time timestamp without time zone,
    note text
);


CREATE SEQUENCE user_navigations_id_seq;
ALTER TABLE user_navigations ALTER COLUMN id SET DEFAULT nextval('user_navigations_id_seq');
ALTER TABLE user_navigations ALTER COLUMN id SET NOT NULL;
ALTER SEQUENCE user_navigations_id_seq OWNED BY user_navigations.id;    -- 8.2 or later
SELECT setval('user_navigations_id_seq', 1);  