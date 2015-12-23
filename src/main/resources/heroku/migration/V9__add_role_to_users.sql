
-- ALTER TABLE users DROP COLUMN role;

ALTER TABLE users ADD COLUMN role character varying(50);
ALTER TABLE users ALTER COLUMN role SET NOT NULL;
ALTER TABLE users ALTER COLUMN role SET DEFAULT ''::character varying;

