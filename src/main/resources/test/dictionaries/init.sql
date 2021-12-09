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


INSERT INTO dictionaries(name, "schema")
  VALUES('fish_rate', '{"title": "fish_rate",
                         "type": "object",
                         "additionalProperties": false,
                         "properties": {
                           "rate": {
                             "type": "integer",
                             "minimum": 1,
                             "maximum": 100
                           },
                           "maxSize": {
                             "type": "integer",
                             "minimum": 1
                           }
                         },
                         "required": ["rate", "maxSize"]
                       }');

INSERT INTO dictionaries(name, "schema")
  VALUES('test_dict', '{"title": "test_dict",
                         "type": "object",
                         "additionalProperties": false,
                         "properties": {
                           "rate": {
                             "type": "integer",
                             "minimum": 1,
                             "maximum": 10
                           },
                           "maxSize": {
                             "type": "integer",
                             "minimum": 1
                           }
                         },
                         "required": ["rate", "maxSize"]
                       }');

DO
'
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name=''fish_rate'') LOOP
    INSERT INTO dictionaries_items (id, dictionary_id, dictionary_name, archive, item) VALUES(1, x.id, ''fish_rate'', false, ''{"rate": 2, "archive": false, "maxSize": 9999999, "product": "Рыбы семейства лососевых", "productCode": "SALMON"}'');
    INSERT INTO dictionaries_items (id, dictionary_id, dictionary_name, archive, item) VALUES(2, x.id, ''fish_rate'', false, ''{"rate": 20, "archive": false, "maxSize": 100, "product": "Рыбы семейства осетровых", "productCode": "STURGEON"}'');
        /*id=3 -> archive=true*/
    INSERT INTO dictionaries_items (id, dictionary_id, dictionary_name, archive, item) VALUES(3, x.id, ''fish_rate'', true, ''{"rate": 8, "archive": true, "maxSize": 500, "product": "Икра осетровых видов рыб", "productCode": "CAVIAR"}'');
    INSERT INTO dictionaries_items (id, dictionary_id, dictionary_name, archive, item) VALUES(4, x.id, ''fish_rate'', false, ''{"rate": 8, "archive": false, "maxSize": 443, "product": "Рыбы семейства сомовых", "productCode": "CATFISH"}'');
    INSERT INTO dictionaries_items (id, dictionary_id, dictionary_name, archive, item) VALUES(5, x.id, ''fish_rate'', false, ''{"rate": 2, "archive": false, "maxSize": 100, "product": "Рыбы семейства карповых", "productCode": "CARP"}'');
  END LOOP;
END;
' language plpgsql;

SELECT setval('dictionaries_items_id_seq', 6)



