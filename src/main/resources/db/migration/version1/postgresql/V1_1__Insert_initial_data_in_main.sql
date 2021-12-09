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
  VALUES('gas_accredited_organization', '{"title": "gas_accredited_organization",
                "type": "object",
                "properties": {
                  "organizationName": {
                    "type": "string",
                    "minLength": 1
                  },
                  "chiefFio": {
                    "type": "string",
                    "minLength": 1
                  },
                  "phone": {
                    "type": "string",
                    "pattern": "\\d+"
                  },
                  "registrationAddress": {
                    "type": "string",
                    "minLength": 1
                  },
                  "actualAddress": {
                    "type": "string",
                    "minLength": 1
                  },
                  "inn": {
                    "type": "string",
                    "pattern": "\\d{10}|\\d{12}"
                  },
                  "kpp": {
                    "type": "string",
                    "pattern": "\\d{9}"
                  },
                  "website": {
                    "type": "string"
                  },
                  "required": [ "organizationName", "chiefFio", "phone", "registrationAddress", "actualAddress, "inn", "kpp" ]
              }');

INSERT INTO dictionaries(name, "schema")
  VALUES('gas_subsidy_rate', '{"title": "gas_subsidy_rate",
                "type": "object",
                "properties": {
                  "maxRate": {
                    "type": "number",
                    "exclusiveMinimum": 0
                  },
                  "maxRateMsp": {
                    "type": "number",
                    "exclusiveMinimum": 0
                  },
                "required": [ "maxRate", "maxRateMsp" ]
              }');

INSERT INTO dictionaries(name, "schema")
  VALUES('edo', '{"title": "edo",
                   "type": "object",
                   "properties": {
                     "name": {
                       "type": "string",
                       "minLength": 1
                     },
                   "required": [ "name" ]
                 }');

INSERT INTO dictionaries(name, "schema")
  VALUES('resident', '{"title": "resident",
                        "type": "object",
                        "properties": {
                          "name": {
                            "type": "string",
                            "minLength": 1
                          },
                          "ogrn": {
                            "type": "string",
                            "pattern": "\\d{13}|\\d{15}"
                          },
                          "inn": {
                            "type": "string",
                            "pattern": "\\d{10}|\\d{12}"
                          },
                          "contract": {
                            "type": "string",
                            "minLength": 1
                          },
                          "residentType": {
                            "type": "string",
                            "minLength": 1
                          },
                          "required": [ "name", "ogrn", "inn", "contract", "residentType ]
                      }');

INSERT INTO dictionaries(name, "schema")
  VALUES('tech', '{"title": "technology",
                    "type": "object",
                    "properties": {
                      "name": {
                        "type": "string",
                        "minLength": 1
                      },
                    "required": [ "name" ]
                  }');

INSERT INTO dictionaries(name, "schema")
  VALUES('machinery_rate', '{"title": "machinery_rate",
                "type": "object",
                "properties": {
                  "rate": {
                    "type": "number",
                    "minimum": 1,
                    "maximum": 100
                  },
                  "maxSize": {
                    "type": "number",
                    "exclusiveMinimum": 0,
                    "multipleOf" : 0.001,
                    "maximum": 99999999
                  },
                "required": [ "rate", "maxSize" ]
              }');

INSERT INTO dictionaries(name, "schema")
  VALUES('project', '{"title": "project",
                "type": "object",
                "properties": {
                  "name": {
                    "type": "string",
                    "minLength": 1
                  },
                  "type": {
                    "type": "string",
                    "minLength": 1
                  },
                  "required": [ "name", "type" ]
              }');
