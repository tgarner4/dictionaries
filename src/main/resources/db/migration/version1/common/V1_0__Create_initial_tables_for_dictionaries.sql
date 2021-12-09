CREATE TABLE IF NOT EXISTS dictionaries (
	id bigserial PRIMARY KEY,
	name varchar(100) NOT NULL,
	"schema" varchar
);

CREATE UNIQUE INDEX IF NOT EXISTS dictionaries_id_uidx ON dictionaries(name);

CREATE TABLE IF NOT EXISTS dictionaries_items (
	id bigserial PRIMARY KEY,
	dictionary_id bigint NOT NULL,
	dictionary_name varchar(100) NOT NULL,
  archive boolean NOT NULL DEFAULT false,
  item jsonb,
	locale varchar(30)
);

CREATE UNIQUE INDEX IF NOT EXISTS dictionaries_items_idx ON dictionaries_items(dictionary_id, id);

ALTER TABLE IF EXISTS dictionaries_items ADD CONSTRAINT dictionaries_items_dictionary_id_fk
  FOREIGN KEY (dictionary_id) REFERENCES dictionaries(id);