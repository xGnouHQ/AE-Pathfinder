-- ===========================
-- ABTEILUNG
-- ===========================
INSERT INTO ABTEILUNG (NAME) VALUES
('KM11'),
('KM12'),
('KM13'),
('KM21'),
('KM22'),
('KM23'),
('KM31'),
('KM32'),
('KM41'),
('KM42'),
('KM43'),
('KM51'),
('KM52'),
('KM53'),
('KM61'),
('KM62'),
('KM81'),
('KM82'),
('KM83'),
('IBS11'),
('IBS12'),
('IBS13'),
('IBS21'),
('IBS22'),
('IBS23'),
('IBS31'),
('IBS32'),
('IBS41'),
('IBS42'),
('IBS43'),
('IBS51'),
('IBS52'),
('IBS53'),
('IBS61'),
('IBS62'),
('IBS81'),
('IBS82'),
('IBS83');

-- ===========================
-- SERVICEBEREICHSLEITER
-- ===========================
INSERT INTO SERVICEBEREICHSLEITER (ABTEILUNG_ID, EMAIL, ERSTELLT_AM, KONTAKTPERSON, TELEFONNUMMER) VALUES
(1,  'sara.schmidt@muenchen.de',       '2024-01-10', 'Sara Schmidt',       '089-210011'),
(2,  'lukas.wagner@muenchen.de',       '2024-01-10', 'Lukas Wagner',       '089-210012'),
(3,  'marie.schneider@muenchen.de',    '2024-01-10', 'Marie Schneider',    '089-210013'),
(4,  'jan.fischer@muenchen.de',        '2024-01-10', 'Jan Fischer',        '089-210021'),
(5,  'laura.weber@muenchen.de',        '2024-01-10', 'Laura Weber',        '089-210022'),
(6,  'paul.hofmann@muenchen.de',       '2024-01-10', 'Paul Hofmann',       '089-210023'),
(7,  'nina.bauer@muenchen.de',         '2024-01-10', 'Nina Bauer',         '089-210031'),
(8,  'felix.richter@muenchen.de',      '2024-01-10', 'Felix Richter',      '089-210032'),
(9,  'julia.koch@muenchen.de',         '2024-01-10', 'Julia Koch',         '089-210041'),
(10, 'tobias.brandt@muenchen.de',      '2024-01-10', 'Tobias Brandt',      '089-210042'),
(11, 'marina.fuchs@muenchen.de',       '2024-01-10', 'Marina Fuchs',       '089-210043'),
(12, 'daniel.schuster@muenchen.de',    '2024-01-10', 'Daniel Schuster',    '089-210051'),
(13, 'kathrin.vogel@muenchen.de',      '2024-01-10', 'Kathrin Vogel',      '089-210052'),
(14, 'michael.friedrich@muenchen.de',  '2024-01-10', 'Michael Friedrich',  '089-210053'),
(15, 'lisa.lehmann@muenchen.de',       '2024-01-10', 'Lisa Lehmann',       '089-210061'),
(16, 'jonas.graf@muenchen.de',         '2024-01-10', 'Jonas Graf',         '089-210062'),
(17, 'helen.schmid@muenchen.de',       '2024-01-10', 'Helen Schmid',       '089-210081'),
(18, 'marc.keller@muenchen.de',        '2024-01-10', 'Marc Keller',        '089-210082'),
(19, 'sophie.kraus@muenchen.de',       '2024-01-10', 'Sophie Kraus',       '089-210083'),
(20, 'andreas.pohl@muenchen.de',       '2024-01-10', 'Andreas Pohl',       '089-210111'),
(21, 'elena.hartmann@muenchen.de',     '2024-01-10', 'Elena Hartmann',     '089-210112'),
(22, 'sebastian.seidel@muenchen.de',   '2024-01-10', 'Sebastian Seidel',   '089-210113'),
(23, 'maria.baumann@muenchen.de',      '2024-01-10', 'Maria Baumann',      '089-210121'),
(24, 'christian.maier@muenchen.de',    '2024-01-10', 'Christian Maier',    '089-210122'),
(25, 'jana.pfeifer@muenchen.de',       '2024-01-10', 'Jana Pfeifer',       '089-210123'),
(26, 'florian.winter@muenchen.de',     '2024-01-10', 'Florian Winter',     '089-210131'),
(27, 'verena.schill@muenchen.de',      '2024-01-10', 'Verena Schill',      '089-210132'),
(28, 'carsten.hahn@muenchen.de',       '2024-01-10', 'Carsten Hahn',       '089-210141'),
(29, 'katrin.marquardt@muenchen.de',   '2024-01-10', 'Katrin Marquardt',   '089-210142'),
(30, 'tim.kunze@muenchen.de',          '2024-01-10', 'Tim Kunze',          '089-210143'),
(31, 'hannah.berg@muenchen.de',        '2024-01-10', 'Hannah Berg',        '089-210151'),
(32, 'marco.loew@muenchen.de',         '2024-01-10', 'Marco Löw',          '089-210152'),
(33, 'karin.schwarz@muenchen.de',      '2024-01-10', 'Karin Schwarz',      '089-210153'),
(34, 'patrick.jung@muenchen.de',       '2024-01-10', 'Patrick Jung',       '089-210161'),
(35, 'melanie.herrmann@muenchen.de',   '2024-01-10', 'Melanie Herrmann',   '089-210162'),
(36, 'frank.stahl@muenchen.de',        '2024-01-10', 'Frank Stahl',        '089-210181'),
(37, 'tanja.krueger@muenchen.de',      '2024-01-10', 'Tanja Krüger',       '089-210182'),
(38, 'manuel.beck@muenchen.de',        '2024-01-10', 'Manuel Beck',        '089-210183');

-- ===========================
-- NACHWUCHSKRAFT
-- ===========================
INSERT INTO NACHWUCHSKRAFT
(JAHRGANG, EMAIL, ERSTELLT_AM, NACHNAME, PERSONALNUMMER, STUDIENRICHTUNG, VORNAME)
VALUES
('2022/2025', 'max.mustermann@muenchen.de', '2024-01-05 09:00:00', 'Mustermann', 'P1001', 'Wirtschaftsinformatik', 'Max'),
('2023/2026', 'sophie.meier@muenchen.de',     '2024-02-12 10:00:00', 'Meier',       'P1002', 'Informatik',              'Sophie'),
('2021/2024', 'leon.schneider@muenchen.de',   '2024-03-18 11:00:00', 'Schneider',   'P1003', 'BWL',                     'Leon'),
('2024/2027', 'lena.huber@muenchen.de',       '2024-04-22 12:00:00', 'Huber',       'P1004', 'Verwaltungsinformatik',   'Lena'),
('2023/2026', 'jonas.keller@muenchen.de',         '2024-01-10 09:00:00', 'Keller',      'P1005', 'Informatik',              'Jonas'),
('2022/2025', 'mia.schramm@muenchen.de',          '2024-01-15 14:00:00', 'Schramm',     'P1006', 'Wirtschaftsinformatik',   'Mia'),
('2024/2027', 'felix.hartmann@muenchen.de',       '2024-02-01 10:30:00', 'Hartmann',    'P1007', 'Data Science',            'Felix'),
('2021/2024', 'luca.braun@muenchen.de',           '2024-02-07 08:45:00', 'Braun',       'P1008', 'BWL',                     'Luca'),
('2025/2028', 'sarah.weiss@muenchen.de',          '2024-03-02 11:20:00', 'Weiss',       'P1009', 'Wirtschaftsinformatik',   'Sarah'),
('2023/2026', 'tom.schulz@muenchen.de',           '2024-03-10 12:10:00', 'Schulz',      'P1010', 'Informatik',              'Tom'),
('2022/2025', 'laura.stein@muenchen.de',          '2024-03-12 09:50:00', 'Stein',       'P1011', 'Public Management',       'Laura'),
('2024/2027', 'paul.neumann@muenchen.de',         '2024-03-14 13:40:00', 'Neumann',     'P1012', 'Verwaltungsinformatik',   'Paul'),
('2023/2026', 'klara.jung@muenchen.de',           '2024-03-20 08:30:00', 'Jung',        'P1013', 'Wirtschaftsinformatik',   'Klara'),
('2021/2024', 'david.schuster@muenchen.de',       '2024-03-25 10:15:00', 'Schuster',    'P1014', 'BWL',                     'David'),
('2022/2025', 'nina.frank@muenchen.de',           '2024-04-02 11:05:00', 'Frank',       'P1015', 'Informatik',              'Nina'),
('2025/2028', 'martin.loew@muenchen.de',          '2024-04-10 09:55:00', 'Loew',        'P1016', 'Data Science',            'Martin'),
('2023/2026', 'eline.moser@muenchen.de',          '2024-04-15 12:20:00', 'Moser',       'P1017', 'Wirtschaftsinformatik',   'Eline'),
('2024/2027', 'julian.kraft@muenchen.de',         '2024-04-18 13:50:00', 'Kraft',       'P1018', 'Informatik',              'Julian'),
('2022/2025', 'amelie.berger@muenchen.de',        '2024-04-22 14:30:00', 'Berger',      'P1019', 'Public Management',       'Amelie'),
('2023/2026', 'tim.lorenz@muenchen.de',           '2024-04-25 15:10:00', 'Lorenz',      'P1020', 'Wirtschaftsinformatik',   'Tim'),
('2021/2024', 'sandra.kunz@muenchen.de',          '2024-04-28 09:10:00', 'Kunz',        'P1021', 'BWL',                     'Sandra'),
('2024/2027', 'benjamin.herrmann@muenchen.de',    '2024-05-01 11:40:00', 'Herrmann',    'P1022', 'Informatik',              'Benjamin'),
('2022/2025', 'julia.adler@muenchen.de',          '2024-05-03 10:25:00', 'Adler',       'P1023', 'Wirtschaftsinformatik',   'Julia'),
('2025/2028', 'kevin.maier@muenchen.de',          '2024-05-05 12:55:00', 'Maier',       'P1024', 'Data Science',            'Kevin');

-- ===========================
-- WUNSCHABTEILUNG
-- ===========================
INSERT INTO WUNSCHABTEILUNG (NACHWUCHSKRAFT_ID, ABTEILUNG_ID) VALUES
(1, 1),
(1, 2),
(1, 4),
(2, 1),
(2, 5),
(3, 3),
(4, 4),
(4, 5);

-- ===========================
-- STELLE
-- ===========================
INSERT INTO STELLE (BESCHREIBUNG, BEWERBUNGSFRIST, STANDORT, STATUS, TITEL, REFERAT, ENTGELTGRUPPE, SERVICEBEREICHSLEITER_ID) VALUES
('Softwareentwicklung und Betreuung interner Tools', '2025-01-31', 'München', 'OFFEN', 'Softwareentwickler (m/w/d)', 'it@M', 'E9', 1),
('Weiterentwicklung kommunaler Webportale', '2025-02-15', 'München', 'OFFEN', 'Full-Stack Entwickler (m/w/d)', 'it@M', 'E10', 4),
('Backend-Services und API-Integration', '2025-02-28', 'München', 'OFFEN', 'Java Backend Entwickler (m/w/d)', 'it@M', 'E9', 7),
('First- und Second-Level Support für Fachverfahren', '2025-01-20', 'München', 'OFFEN', 'IT-Support Spezialist (m/w/d)', 'it@M', 'E7', 9),
('Analyse und Optimierung kommunaler Datenbanken', '2025-03-10', 'München', 'OFFEN', 'Datenbankadministrator (m/w/d)', 'it@M', 'E11', 11),
('CI/CD-Automatisierung und Deployments', '2025-02-05', 'München', 'OFFEN', 'DevOps Engineer (m/w/d)', 'it@M', 'E10', 14),
('Entwicklung interner Web-Anwendungen', '2025-04-01', 'München', 'OFFEN', 'Webentwickler (m/w/d)', 'it@M', 'E8', 15),
('Security-Monitoring und Schwachstellenanalyse', '2025-02-28', 'München', 'OFFEN', 'IT-Security Analyst (m/w/d)', 'it@M', 'E11', 17),
('Modernisierung bestehender Fachanwendungen', '2025-03-15', 'München', 'OFFEN', 'Anwendungsentwickler (m/w/d)', 'it@M', 'E9', 18),
('Automatisierung administrativer Abläufe', '2025-03-07', 'München', 'OFFEN', 'Applikationsentwickler (m/w/d)', 'it@M', 'E9', 20),
('Identity- & Access-Management', '2025-02-12', 'München', 'OFFEN', 'IAM Spezialist (m/w/d)', 'it@M', 'E10', 21),
('Monitoring und Systemüberwachung', '2025-04-30', 'München', 'OFFEN', 'Systemadministrator (m/w/d)', 'it@M', 'E8', 22),
('Netzwerkanalyse und Firewall-Betreuung', '2025-05-10', 'München', 'OFFEN', 'Netzwerktechniker (m/w/d)', 'it@M', 'E7', 24),
('Frontend-Entwicklung moderner Webanwendungen', '2025-03-02', 'München', 'OFFEN', 'Frontend Entwickler (m/w/d)', 'it@M', 'E9', 27),
('Betrieb von Virtualisierungssystemen', '2025-02-28', 'München', 'OFFEN', 'Virtualisierungstechniker (m/w/d)', 'it@M', 'E10', 28),
('IT-Incident Response und Störungsanalyse', '2025-04-15', 'München', 'OFFEN', 'IT-Betriebssteuerung (m/w/d)', 'it@M', 'E11', 29),
('Testautomatisierung interner Softwarelösungen', '2025-02-21', 'München', 'OFFEN', 'Testautomatisierer (m/w/d)', 'it@M', 'E9', 30),
('Betreuung und Weiterentwicklung kommunaler Fachverfahren', '2025-04-20', 'München', 'OFFEN', 'Fachanwendungsbetreuer (m/w/d)', 'it@M', 'E8', 32),
('Datenintegration und ETL-Entwicklung', '2025-04-05', 'München', 'OFFEN', 'ETL Entwickler (m/w/d)', 'it@M', 'E10', 33),
('Betrieb interner Cloud-Dienste', '2025-03-27', 'München', 'OFFEN', 'Cloud Engineer (m/w/d)', 'it@M', 'E11', 34),
('Entwicklung technischer Schnittstellen', '2025-03-30', 'München', 'OFFEN', 'Schnittstellenentwickler (m/w/d)', 'it@M', 'E9', 36),
('Rollout und Betreuung neuer Arbeitsplatzsysteme', '2025-04-12', 'München', 'OFFEN', 'IT-Techniker (m/w/d)', 'it@M', 'E7', 37),
('Administration zentraler Webserver', '2025-05-01', 'München', 'OFFEN', 'Webserver-Administrator (m/w/d)', 'it@M', 'E10', 38),
('Softwarepaketierung und Deployment-Automatisierung', '2025-03-22', 'München', 'OFFEN', 'Client Engineer (m/w/d)', 'it@M', 'E8', 12),

('Koordination von IT-Betriebsteams', '2025-04-30', 'München', 'OFFEN', 'Teamleitung IT-Betrieb (m/w/d)', 'it@M', 'A10', 5),
('Steuerung kommunaler Softwareprojekte', '2025-03-10', 'München', 'OFFEN', 'Projektleitung IT (m/w/d)', 'it@M', 'A12', 8),
('Qualitäts- und Prozessmanagement in der IT', '2025-02-17', 'München', 'OFFEN', 'IT-Prozessmanager (m/w/d)', 'it@M', 'A9', 13),
('Planung digitaler Infrastruktur und IT-Strategie', '2025-05-15', 'München', 'OFFEN', 'IT-Architekt (m/w/d)', 'it@M', 'A11', 16),
('Operative Leitung Anwendungsbetrieb', '2025-04-08', 'München', 'OFFEN', 'Gruppenleitung Anwendungstechnik (m/w/d)', 'it@M', 'A10', 19),
('IT-Sicherheitsmanagement und Compliance', '2025-03-19', 'München', 'OFFEN', 'IT-Sicherheitsbeauftragter (m/w/d)', 'it@M', 'A12', 23);


-- ===========================
-- TAG
-- ===========================
INSERT INTO TAG (NAME) VALUES
('Java'),
('Spring Boot'),
('Projektmanagement'),
('Teamarbeit'),
('Kommunikation'),
('Datenanalyse'),
('Bürgerdienste'),
('Stadtplanung'),
('DevOps'),
('CI/CD'),
('Docker'),
('Kubernetes'),
('Linux'),
('Windows Client'),
('Datenbanken'),
('SQL'),
('Security'),
('Netzwerk'),
('Frontend'),
('Backend'),
('Cloud'),
('Monitoring'),
('Support'),
('Incident Response'),
('ETL'),
('Identity & Access'),
('Virtualisierung'),
('Webentwicklung');

-- ===========================
-- STELLE_TAG
-- ==========================
INSERT INTO STELLE_TAG (STELLE_ID, TAG_ID) VALUES
(1, 1), (1, 2), (1, 20), (1, 28),
(2, 1), (2, 19), (2, 20), (2, 28),
(3, 1), (3, 2), (3, 20), (3, 16),
(4, 23), (4, 14), (4, 4), (4, 5),
(5, 15), (5, 16), (5, 6),
(6, 9), (6, 10), (6, 11), (6, 12),
(7, 19), (7, 28), (7, 4),
(8, 17), (8, 23), (8, 24),
(9, 1), (9, 20), (9, 28),
(10, 19), (10, 20), (10, 15),
(11, 26), (11, 17), (11, 24),
(12, 13), (12, 22), (12, 17),
(13, 18), (13, 17), (13, 24),
(14, 19), (14, 28), (14, 4),
(15, 27), (15, 13), (15, 22),
(16, 22), (16, 24), (16, 23),
(17, 9), (17, 10), (17, 19), (17, 20),
(18, 23), (18, 4), (18, 5),
(19, 25), (19, 6), (19, 15),
(20, 21), (20, 13), (20, 22),
(21, 20), (21, 16), (21, 28),
(22, 23), (22, 14), (22, 4),
(23, 13), (23, 28), (23, 22),
(24, 14), (24, 23), (24, 4),
(25, 3), (25, 4), (25, 22),
(26, 3), (26, 5), (26, 4),
(27, 3), (27, 4), (27, 6),
(28, 9), (28, 21), (28, 17),
(29, 3), (29, 4), (29, 20),
(30, 17), (30, 24), (30, 21);

-- ===========================
-- BEWERBUNG
-- ===========================
INSERT INTO BEWERBUNG (EINGEREICHT_AM, KOMMENTAR, STATUS, NACHWUCHSKRAFT_ID, STELLE_ID)
VALUES
('2024-05-20', 'Ich freue mich auf diese spannende Position!', 'EINGEREICHT', 1, 1),
('2024-06-15', 'Meine Kenntnisse passen sehr gut zur Stelle.', 'EINGEREICHT', 2, 2),
('2024-07-10', 'Habe bereits Erfahrung im Bereich Controlling.', 'EINGEREICHT', 3, 2),
('2024-08-02', 'Motiviert und bereit für neue Aufgaben.', 'EINGEREICHT', 4, 4);

-- ===========================
-- NACHWUCHSKRAFT_ANHANG
-- ===========================
INSERT INTO NACHWUCHSKRAFT_ANHANG (DATEIPFAD, HOCHGELADEN_AM, TYP, NACHWUCHSKRAFT_ID)
VALUES
('/uploads/lebenslauf_max.pdf', '2024-05-10', 'Lebenslauf', 1),
('/uploads/zeugnis_sophie.pdf', '2024-06-02', 'Zeugnis', 2),
('/uploads/motivation_leon.pdf', '2024-06-20', 'Motivationsschreiben', 3),
('/uploads/lebenslauf_lena.pdf', '2024-07-05', 'Lebenslauf', 4);

-- ===========================
-- STELLENMERKLISTE
-- ===========================
INSERT INTO STELLENMERKLISTE (ERSTELLT_AM, MERKEN_STATUS, NACHWUCHSKRAFT_ID, STELLE_ID)
VALUES
('2024-06-01', TRUE, 1, 1),
('2024-06-05', TRUE, 2, 2),
('2024-06-10', FALSE, 3, 3),
('2024-07-01', TRUE, 4, 4);

-- ===========================
-- NACHWUCHSKRAFT_INTERESSE
-- ===========================
INSERT INTO NACHWUCHSKRAFT_INTERESSE (NACHWUCHSKRAFT_ID, TAG_ID) VALUES
(1, 1), (1, 2), (1, 20), (1, 28),
(2, 3), (2, 4), (2, 19), (2, 5),
(3, 6), (3, 15), (3, 16),
(4, 7), (4, 4), (4, 3), (4, 5),
(5, 1), (5, 2), (5, 20), (5, 16),
(6, 19), (6, 28), (6, 4), (6, 5),
(7, 9), (7, 10), (7, 11), (7, 12),
(8, 6), (8, 15), (8, 16), (8, 25),
(9, 3), (9, 4), (9, 5),
(10, 1), (10, 20), (10, 28),
(11, 13), (11, 22), (11, 17),
(12, 18), (12, 17), (12, 24),
(13, 19), (13, 28), (13, 4),
(14, 27), (14, 13), (14, 22),
(15, 6), (15, 15), (15, 25),
(16, 21), (16, 13), (16, 22),
(17, 20), (17, 16), (17, 28),
(18, 23), (18, 14), (18, 4), (18, 5),
(19, 3), (19, 4), (19, 22),
(20, 3), (20, 5), (20, 6),
(21, 9), (21, 21), (21, 17),
(22, 1), (22, 2), (22, 20),
(23, 17), (23, 24), (23, 21),
(24, 19), (24, 4), (24, 28), (24, 20);

-- ===========================
-- NACHWUCHSKRAFT_PRAKTIKUM
-- ===========================
INSERT INTO NACHWUCHSKRAFT_PRAKTIKUM (NACHWUCHSKRAFT_ID, ABTEILUNG_ID)
VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 3),
(4, 4);