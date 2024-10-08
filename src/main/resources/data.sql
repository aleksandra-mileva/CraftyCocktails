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

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (8, "RUM", "Листенцата мента се накъсват, като леко се притриват с пръсти и пускат на дъното на чашата.

Добавя се тръстиковата захар сок от половин лайм.

С помощта на дървена лъжица или чукало ментата се счуква отново, за да пусне аромата си.

В чашата се слагат кубчета лед, който се залива с качествен бял ром и газирана вода.

Чашата с Мохито се декорира с резенчета лайм и коктейлът е готов за сервиране.

Коктейл Мохито е изключително приятен, свеж и има вкус на почивка или още по точно - на летен бриз.",
"SWEET",
"Мохито", 2,
"12 бр. пресни листа мента
4 ч.л. тръстикова захар
1 лайм
кубчета лед
60 мл. бял ром Havana Club
200 мл газирана вода",
10, "https://www.youtube.com/watch?v=KWU9ZaWbeuQ", 2);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (8, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (9, "WHISKEY", "Добавете бърбън, лимонов сок, захарен сироп и яйчен белтък към шейкър и разклатете добре за 30 секунди без лед.

Добавете лед и разклатете отново, докато се охлади добре.

Прецедете в чаша.",
"SOUR",
"Уиски Сауър", 1,
"2 унции бърбън
¾ чаша лимонов сок
½ унции захарен сироп сироп
½ унции яйчен белтък (по избор)",
14, "https://www.youtube.com/watch?v=kfB1vLhz2Pw", 2);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (9, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (10, "GIN", "В чаша пълна с лед се наливат джинът (30 мл.), вермутът (30 мл.) и кампарито (30 мл.).

Коктейлът се разбърква и се украсява с резенчета портокал.",
"BITTER",
"Негрони", 1,
"30 мл. джин
30 мл. сладък вермут
30 мл. кампари
2 резенчета портокал
лед",
24, "https://www.youtube.com/watch?v=JFggu73t9gA", 2);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (10, 1);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (11, "NONE", "Пъпешът се обелва, семките се отстраняват и меката част се пасира до пюре.

Добавя се сладоледът и сместа се пасира отново.

Босилекът се нарязва на ситно и се добавя към пюрето от пъпеш.

Накрая се добавя натрошеният лед, коктейлът се разпределя в чаши и се сервира веднага с широки сламки.",
"SWEET",
"Пъпешов коктейл", 1,
"1 кг пъпеш
200 г ванилов сладолед
4 стръка босилек
200 г натрошен лед",
0, "https://www.youtube.com/watch?v=wocu1HTj0Rc", 1);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (11, 2);

INSERT INTO cocktails(id, spirit, preparation, flavour, name, servings, ingredients, percent_alcohol, video_url, author_id)
VALUES (12, "NONE", "Листенцата на ментата се накъсват. Лимонът и портокалът се заливат с гореща вода и се изцеждат.

Кората на динята се маха, вътрешността се реже на парчета, семките се отстраняват.

Динята се пасира заедно с меда, лимона и портокала.

Коктейлът се оставя за 1 час в хладилника и се сервира с лед и листенца мента.",
"SWEET",
"Коктейл с диня", 1,
"1 бр. лимон
1 бр. портокал
1 кг диня
3 ч.л. мед
2 стръка мента
2 ч.ч. на кубчета лед",
        0, "https://www.youtube.com/watch?v=2zUzILH4To4", 2);

INSERT INTO cocktails_types(cocktail_entity_id, types_id) values (12, 2);

INSERT INTO pictures(id, public_id, title, url, author_id, cocktail_id) VALUES
                                                                          (1, "rfx5pdjd6cpn3pbbrtcm", "PinaColada", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723493424/rfx5pdjd6cpn3pbbrtcm.jpg", 1, 1),
                                                                          (2, "kgv4ekkxr9goboq44sfg", "Manhatten", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/kgv4ekkxr9goboq44sfg.png", 1, 2),
                                                                          (3, "dicy7bijiddeksrawdz8", "teqilaSunrise", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723493458/dicy7bijiddeksrawdz8.jpg", 1, 3),
                                                                          (4, "mz8brm4guvo2ghme4lcv", "Martini", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723378829/mz8brm4guvo2ghme4lcv.png", 1, 4),
                                                                          (5, "o3vakwrhgrzlgrctp4gs", "BloodyMary", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/o3vakwrhgrzlgrctp4gs.jpg", 1, 5),
                                                                          (6, "exkybjsjyonsib8b4lnf", "BrandySour", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/exkybjsjyonsib8b4lnf.jpg", 1, 6),
                                                                          (7, "w4ukqvhml1zkqydqhel1", "mojitoVirgin", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723131202/w4ukqvhml1zkqydqhel1.jpg", 1, 7),
                                                                          (8, "duhvmdz81pzmlgdzq7cx", "mojito", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723377633/duhvmdz81pzmlgdzq7cx.jpg", 2, 8),
                                                                          (9, "ueqtcmj58dkrwndpshiq", "whiskeySour", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723546562/ueqtcmj58dkrwndpshiq.jpg", 2, 9),
                                                                          (10, "vbhxtfkuxk32x9qxrfno", "negroni", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723378386/vbhxtfkuxk32x9qxrfno.png", 2, 10),
                                                                          (11, "Screenshot_2024-08-11_at_15.41.09_laysak", "melon", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723380112/Screenshot_2024-08-11_at_15.41.09_laysak.png", 2, 11),
                                                                          (12, "smni2t0omh2dtc6cihry", "melon", "https://res.cloudinary.com/dlknl4mzd/image/upload/v1723380302/smni2t0omh2dtc6cihry.png", 2, 12);









































