DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='fish_rate') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'fish_rate', '{"rate": 2, "archive": false, "maxSize": 9999999, "product": "Рыбы семейства лососевых", "productCode": "SALMON"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'fish_rate', '{"rate": 20, "archive": false, "maxSize": 100, "product": "Рыбы семейства осетровых", "productCode": "STURGEON"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'fish_rate', '{"rate": 8, "archive": false, "maxSize": 500, "product": "Икра осетровых видов рыб", "productCode": "CAVIAR"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'fish_rate', '{"rate": 8, "archive": false, "maxSize": 443, "product": "Рыбы семейства сомовых", "productCode": "CATFISH"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'fish_rate', '{"rate": 2, "archive": false, "maxSize": 100, "product": "Рыбы семейства карповых", "productCode": "CARP"}');
  END LOOP;
END$$;


DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='gas_accredited_organization') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1643010350", "kpp": "111111111", "phone": "89178528063", "archive": false, "chiefFio": "Шайхлисламов Рамиль Раисович", "actualAddress": "Республика Татарстан, г. Азнакаево, ул. Николаева, д.10", "organizationName": "ООО \"Фривей\"", "registrationAddress": "423330, Республика Татарстан, г. Азнакаево, ул. М.Хасанова, д.12"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1657193505", "kpp": "111111111", "phone": "89877788505", "archive": false, "chiefFio": "Медведев Александр Николаевич", "actualAddress": "420053, Республика Татарстан, г. Казань, ул. Сибирский тракт , д.48", "organizationName": "ООО \"Сервис+\"", "registrationAddress": "420094, Республика Татарстан, г. Казань, ул. Короленко, д. 58, корп. 20 А, каб. 5"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1639037510", "kpp": "111111111", "phone": "89093091000", "archive": false, "website": "https://tehcentrraritek.business.site/", "chiefFio": "Прохоров Иван Александрович", "actualAddress": "Республика Татарстан, г. Набережные Челны, Казанский проспект, 252", "organizationName": "ООО \"Техцентр РариТЭК\"", "registrationAddress": "423895, Республика Татарстан, Тукаевский р-н, с. Нижний Суык-Су, ул. Сармановская, 12"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1661040082", "kpp": "111111111", "phone": "89872251315", "archive": false, "chiefFio": "Фахретдинов Мансур Асхатович", "actualAddress": "Республика Татарстан, г. Казань, ул. Тэцевская, 10/4", "organizationName": "ООО \"ГазоДизельные системы\"", "registrationAddress": "420047, Республика Татарстан, г.Казань, ул.Кутузова, д.17А, кв. 1"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1661036262", "kpp": "111111111", "phone": "89872251315", "archive": false, "website": "https://dizel-gaz.ru/", "chiefFio": "Иванов Тигран Артурович", "actualAddress": "Республика Татарстан, г. Казань, ул. Тэцевская, 10/4", "organizationName": "ООО \"АвтоЭкоСистемы\"", "registrationAddress": "420047, Республика Татарстан, г.Казань, ул.Кутузова, д.17А, кв. 1"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "165604779647", "kpp": "111111111", "phone": "88432767575", "archive": false, "chiefFio": "Салахиев Ильнур Шайхуллович", "actualAddress": "Республика Татарстан, г. Казань, ул. Аделя Кутуя, 90", "organizationName": "ИП Салахиев Ильнур Шайхуллович", "registrationAddress": "420030, Республика Татарстан, г. Казань, ул. Большая, д.2"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1639057330", "kpp": "111111111", "phone": "89625677515", "archive": false, "website": "http://alfa-gbo.ru/", "chiefFio": "Жеребцов Глеб Александрович", "actualAddress": "Республика Татарстан, Зеленодольский район, п. Новониколаевский,  ул.Центральная, д. 1б", "organizationName": "ООО \"АльфаСервис\"", "registrationAddress": "423872, Республика Татарстан, Тукаевский район, п.Новый, ул.Центральная, д.1, пом.9"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "1646014842", "kpp": "111111111", "phone": "88005500088", "archive": false, "website": "https://www.mtz-tatarstan.ru/", "chiefFio": "Анисимов Константин Владимирович", "actualAddress": "Республика Татарстан, Елабужский район, промышленная площадка «Алабуга», улица 9, корпус 1", "organizationName": "ООО \"ТПК МТЗ-Татарстан\"", "registrationAddress": "423600, Республика Татарстан, Елабужский район, промышленная площадка «Алабуга», улица 9, корпус 1"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_accredited_organization', '{"inn": "2312278615", "kpp": "111111111", "phone": "89600655815", "archive": false, "chiefFio": "Телепов Сергей Александрович", "actualAddress": "423806, Республика Татарстан, г. Набережные Челны, ул. им. Низаметдинова Р.М., д. 16 Б, помещение 2", "organizationName": "ООО \"ГазАвторегион\"", "registrationAddress": "423806, Республика Татарстан, г. Набережные Челны, ул. им. Низаметдинова Р.М., д. 16 Б, помещение 2"}');
  END LOOP;
END$$;


DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='gas_subsidy_rate') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 180.0, "maxRateMsp": 270.0, "vehicleCode": "CAR_CARGO_REPAIR", "vehicleType": "Грузовой автомобиль (перевод на газовый цикл - ремоторизация)"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 132.3, "maxRateMsp": 198.45, "vehicleCode": "SEMI_TRUCK", "vehicleType": "Магистральный тягач"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 56.7, "maxRateMsp": 170.1, "vehicleCode": "BUS_8", "vehicleType": "Автобус (до 8 метров)"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 24.3, "maxRateMsp": 72, "vehicleCode": "CAR_1800", "vehicleType": "Легковой автомобиль массой до 1800 кг"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 30.6, "maxRateMsp": 45.9, "vehicleCode": "CAR_2499", "vehicleType": "Легковой автомобиль массой 1800-2499 кг"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 37.8, "maxRateMsp": 56.7, "vehicleCode": "CAR_OVER_2500", "vehicleType": "Легковой автомобиль массой 2500 кг и выше"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 43.2, "maxRateMsp": 64.8, "vehicleCode": "CAR_CARGO_3500", "vehicleType": "Легковой грузовой транспорт (грузоподъемность до 3,5т)"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 99.9, "maxRateMsp": 149.85, "vehicleCode": "BUS_LONG", "vehicleType": "Автобус (свыше 8 метров)"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'gas_subsidy_rate', '{"maxRate": 102.6, "maxRateMsp": 153.9, "vehicleCode": "CAR_CARGO", "vehicleType": "Грузовой автомобиль (перевод в газовый или биотопливный, в том числе газодизельгый цикл)"}');
  END LOOP;
END$$;



DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='edo') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'edo', '{"name": "Такснет", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'edo', '{"name": "СБИС", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'edo', '{"name": "Диадок", "archive": false}');
  END LOOP;
END$$;


DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='technology') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Искусственный интеллект", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Технологии беспроводной связи", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Компоненты робототехники и сенсорика", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Квантовые технологии", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Системы распределенного реестра", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Большие данные", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Технологии дополненной и виртуальной реальности", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Промышленный интернет", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'technology', '{"name": "Новые производственные технологии", "archive": false}');
  END LOOP;
END$$;


DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='machinery_rate') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 3, "archive": false, "maxSize": 7000000.0, "contract": "SALE_PURCHASE", "machinery": "самоходный овощной комбайн", "machineryCode": "VEGETABLEHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 6000000.0, "contract": "SALE_PURCHASE", "machinery": "трактор", "machineryCode": "TRACTOR"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 7000000.0, "contract": "SALE_PURCHASE", "machinery": "зерноуборочный комбайн", "machineryCode": "GRAINHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 7000000.0, "contract": "SALE_PURCHASE", "machinery": "самоходный кормоуборочный комбайн", "machineryCode": "FORAGEHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 10000000, "contract": "SALE_PURCHASE", "machinery": "самоходная жатка (косилка)", "machineryCode": "REAPER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "contract": "SALE_PURCHASE", "machinery": "самоходный картофелеуборочный комбайн", "machineryCode": "POTATOHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 12000000, "contract": "SALE_PURCHASE", "machinery": "самоходный свеклоуборочный комбайн", "machineryCode": "BEETHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 4000000.0, "contract": "SALE_PURCHASE", "machinery": "самоходный опрыскиватель", "machineryCode": "SPRAYER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 6000000.0, "contract": "LEASING", "machinery": "трактор", "machineryCode": "TRACTOR"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 6000000.0, "contract": "LEASING", "machinery": "трактор, приобретенный в 2021", "machineryCode": "TRACTOR2021"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 7000000.0, "contract": "LEASING", "machinery": "зерноуборочный комбайн", "machineryCode": "GRAINHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 7000000.0, "contract": "LEASING", "machinery": "самоходный кормоуборочный комбайн", "machineryCode": "FORAGEHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 3, "archive": false, "contract": "LEASING", "machinery": "самоходный овощной комбайн", "machineryCode": "VEGETABLEHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 7000000.0, "contract": "LEASING", "machinery": "самоходная жатка (косилка)", "machineryCode": "REAPER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 10000000, "contract": "LEASING", "machinery": "самоходный картофелеуборочный комбайн", "machineryCode": "POTATOHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 12000000, "contract": "LEASING", "machinery": "самоходный свеклоуборочный комбайн", "machineryCode": "BEETHARVESTER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 99999990, "contract": "LEASING", "machinery": "другая техника", "machineryCode": "OTHER"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 30, "archive": false, "maxSize": 6000000.0, "contract": "SALE_PURCHASE", "machinery": "трактор, приобретенный в 2021", "machineryCode": "TRACTOR2021"}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'machinery_rate', '{"rate": 100, "archive": false, "maxSize": 10000000, "contract": "SALE_PURCHASE", "machinery": "другая техника", "machineryCode": "OTHER"}');
  END LOOP;
END$$;


DO $$
DECLARE
  x RECORD;
BEGIN
  FOR x IN (SELECT id FROM dictionaries WHERE name='projects') LOOP
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Юридическое лицо, созданное женщиной, являющейся единоличным исполнительным органом юридического лица и (или) женщинами, являющимися учредителями (участниками) юридического лица, а их доля в уставном капитале общества с ограниченной ответственностью, либо складочном капитале хозяйственного товарищества составляет не менее 50 %, либо не менее 50 % голосующих акций акционерного общества", "type": "LEGAL", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "В состав учредителей или акционеров входит физическое лицо старше 45 лет, владеющее не менее 50 % доли в уставном капитале общества с ограниченной ответственностью, либо складочном капитале хозяйственного товарищества, либо не менее 50 % голосующих акций акционерного общества", "type": "LEGAL", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Юридическое лицо — резидент промышленного (индустриального) парка, агропромышленного парка, технопарка, промышленного технопарка, бизнес-инкубатора, коворкинга, расположенного в помещениях центра «Мой бизнес»", "type": "LEGAL", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Индивидуальный предприниматель — резидент промышленного (индустриального) парка, агропромышленного парка, технопарка, промышленного технопарка, бизнес-инкубатора, коворкинга, расположенного в помещениях центра «Мой бизнес»", "type": "BUSINESS", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Индивидуальный предприниматель младше 35 лет", "type": "BUSINESS", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Индивидуальный предприниматель, являющийся членом сельскохозяйственного потребительского кооператива — крестьянским (фермерским) хозяйством", "type": "BUSINESS", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Женщина — индивидуальная предпринимательница", "type": "BUSINESS", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Индивидуальный предприниматель, реализующий проект в сферах туризма, экологии или спорта", "type": "BUSINESS", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Индивидуальный предприниматель старше 45 лет", "type": "BUSINESS", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Самозанятый младше 35 лет", "type": "PERSON", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Самозанятый, реализующий проект в сферах туризма, экологии или спорта", "type": "PERSON", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Самозанятый старше 45 лет, вновь зарегистрированный и действующий менее одного года", "type": "PERSON", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Самозанятый — резидент бизнес-инкубатора (за исключением бизнес-инкубаторов инновационного типа), коворкинга, расположенного в помещениях центра «Мой бизнес»", "type": "PERSON", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "В состав учредителей или акционеров входит физическое лицо до 35 лет, владеющее не менее 50 % доли в уставном капитале общества с ограниченной ответственностью, либо складочном капитале хозяйственного товарищества, либо не менее 50 % голосующих акций акционерного общества", "type": "LEGAL", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Женщина — самозанятая", "type": "PERSON", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Юридическое лицо, реализующее проект в сферах туризма, экологии или спорта", "type": "LEGAL", "archive": false}');
    INSERT INTO dictionaries_items (dictionary_id, dictionary_name, item) VALUES(x.id, 'projects', '{"name": "Юридическое лицо — сельскохозяйственный производственный или потребительский кооператив", "type": "LEGAL", "archive": false}');
  END LOOP;
END$$;
