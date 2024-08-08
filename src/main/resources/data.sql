INSERT INTO types(id, name) VALUES (1, "ALCOHOLIC"),
                                   (2, "NON_ALCOHOLIC");

INSERT INTO roles(id, role) VALUES (1, "USER"),
                                   (2, "MODERATOR"),
                                   (3, "ADMIN");

INSERT INTO users(id, email, first_name, last_name, password, username, account_verified) VALUES
                                                                            (1, "pesho@gmail.com", "Peter", "Petrov", "f0e98959a250b665387e5aa6fa3a3e809105d3e58fde44b43595226285a22e0ce2984efe2fe1b16a", "pesho123", true),
                                                                            (2, "user@example.com", "User", "Userov", "f0e98959a250b665387e5aa6fa3a3e809105d3e58fde44b43595226285a22e0ce2984efe2fe1b16a", "user", true),
                                                                            (3, "admin@example.com", "Admin", "Adminov", "f0e98959a250b665387e5aa6fa3a3e809105d3e58fde44b43595226285a22e0ce2984efe2fe1b16a", "admin", true);

INSERT INTO users_roles(user_entity_id, roles_id) VALUES
                                                      (1, 1), (2, 1), (3, 1), (3, 3);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (1, "RUM", "Белият ром (30 мл.), кокосовата сметана (30 мл.) и сокът от ананас (90 мл.) се смесват с натрошен лед до получаване на еднородна смес.

Налива се в изстудена коктейлна чаша.

Коктейлът Пина Колада се украсява с парченца ананас.

Вместо сок от ананас, най-добре е да направите пресен сок от плода като разбиете няколко резенчета в блендер.",
"SWEET",
"Пина Колада", 1,
"30 мл. бял ром
30 мл. кокосова сметана
90 мл. сок от ананас
5 с.л. натрошен лед
1 резенче ананас",
13, "https://www.youtube.com/watch?v=YaQEaf92z00", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (1, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (2, "WHISKEY", "Този класически коктейл е измислен в Манхатън клуб в Ню Йорк през 1847 година от майката на Уинстън Чърчил – лейди Рандолф Чърчил.

Шейкъра се напълва с лед. Всички продукти се добавят и се смесват добре. Сервира се в чаша за мартини.

Коктейлът Манхатън се украсява с коктейлни черешки и резенчета портокал.",
"BITTER",
"Манхатън", 1,
"25 мл. вермут
75 мл. уиски
5 мл. Angostura bitters
1 резенче портокал
5 коктейлни черешки",
30, "https://www.youtube.com/watch?v=TFWPtkNoF4Y", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (2, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (3, "TEQUILA", "В шейкър се поставя част от леда, текилата (50 мл.) и портокаловия сок (100 мл.) и се разбива за няколко секунди.

В коктейлна чаша се поставя останалият лед и се изсипва течността от шейкъра.

Чашата се накланя леко и се налива гренадинът, който е по-тежък и трябва да отиде на дъното.

Украсява се с резен портокал и коктейлни черешки.",
"SWEET",
"Текила сънрайз", 1,
"50 мл. текила
100 мл. портокалов сок
10 мл. гренадин
10 кубчета лед
2 коктейлни черешки",
12, "https://www.youtube.com/watch?v=QmBkd_Zxv8k", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (3, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (4, "GIN", "В шейкър или друга смесителна чаша се слагат няколко кубчета лед, прибавят се джинът и вермутът и се разбърква енергично.

Готовата напитка се сервира с по едно парченце лимонова кора и по една зелена маслина.",
"SAVORY",
"Мартини", 1,
"50 мл. джин
10 мл. вермут бял сух
2 парченца лимонова кора
2 бр. зелени маслини
лед",
31, "https://www.youtube.com/watch?v=1Jq4tPutdGQ&t=37s", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (4, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (5, "VODKA", "В кана се слагат доматеният сок (250 мл.), лимоновият сок (10 мл.) и водката (80 мл.).

Коктейлът се разбърква и се подправя с по 1 капка сос табаско и сос уорчестър. Поръсва се със сол и черен пипер и се прибавят няколко кубчета лед.

Коктейлът се разбърква и се разпределя в чаши.",
"SAVORY",
"Блъди Мери", 1,
"250 мл. доматен сок
10 мл. лимонов сок
80 мл. водка
1 ч.л. сос табаско
1 ч.л. сос уорчестър
Черен пипер
Сол",
12, "https://www.youtube.com/watch?v=rpEzoWNbgSk", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (5, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (6, "BRANDY", "Във висока чаша за коктейл сложете 3-4 кубчета лед и ½ шайба лимон.

Украсете чашата с 1 шайба лимон и стръкче джоджен, като ги прикрепите към гърлото на чашата.

Върху леда сипете последователно брендито и лимонадата. Във висока чаша за коктейл сложете 3-4 кубчета лед и ½ шайба лимон.

Украсете чашата с 1 шайба лимон и стръкче джоджен, като ги прикрепите към гърлото на чашата.

Върху леда сипете последователно брендито и лимонадата. Най-отгоре капнете няколко Коктейлни капки.",
"SOUR",
"Бренди сауър", 1,
"30-40 мл. Кипърско Бренди или 30 мл. Метакса
20-25 мл. Лимонада
Добре изстудена газирана вода
Няколко капки Коктейлни капки
2 шайби лимон
1 клонче пресен джоджен или мента
3-4 кубчета лед",
15, "https://www.youtube.com/watch?v=v1CeSVEGFNY", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (6, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (7, "NONE", "Листата мента (20 листа) се измиват и се подсушават. Слагат се в чаша и се добавят сока от лайма (1 бр) и пудата захар (2 ч.л.). Намачкват се с дървена лъжица.

Чашата се пълни с лед. Долива се газирана вода и се слагат 2 резена лайм.

Готовото безалкохолно мохито се сервира с листенце прясна мента.",
"SOUR",
"Безалкохолно мохито", 1,
"20 листа мента
2 ч.л. пудра захар
1 лайм
100 мл. газирана вода
6 кубчета лед",
0, "https://www.youtube.com/watch?v=sZbTObGG_L4", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (7, 2);

INSERT INTO pictures(id, public_id, title, url, author_id, cocktail_id) VALUES
                                                                          (1, "fl1ipqtikphbiekb8lcp", "PinaColada", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/fl1ipqtikphbiekb8lcp.png", 1, 1),
                                                                          (2, "kgv4ekkxr9goboq44sfg", "Manhatten", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/kgv4ekkxr9goboq44sfg.png", 1, 2),
                                                                          (3, "usbmwu2oh7goh0ofi44u", "teqilaSunrise", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131203/usbmwu2oh7goh0ofi44u.png", 1, 3),
                                                                          (4, "lqq4ybuzp6lgw3g8xfjd", "Martini", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/lqq4ybuzp6lgw3g8xfjd.jpg", 1, 4),
                                                                          (5, "o3vakwrhgrzlgrctp4gs", "BloodyMary", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/o3vakwrhgrzlgrctp4gs.jpg", 2, 5),
                                                                          (6, "exkybjsjyonsib8b4lnf", "BrandySour", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/exkybjsjyonsib8b4lnf.jpg", 2, 6),
                                                                          (7, "w4ukqvhml1zkqydqhel1", "mojitoVirgin", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/w4ukqvhml1zkqydqhel1.jpg", 2, 7);









































