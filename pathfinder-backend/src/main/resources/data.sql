-- ===========================
-- SERVICEBEREICHSLEITER
-- ===========================
INSERT INTO SERVICEBEREICHSLEITER (BEREICH, EMAIL, ERSTELLT_AM, KONTAKTPERSON, TELEFONNUMMER)
VALUES
('IT-Infrastruktur', 'anna.schmidt@muenchen.de', '2024-01-10', 'Anna Schmidt', '089-123456'),
('Personalmanagement', 'thomas.mueller@muenchen.de', '2024-02-14', 'Thomas Müller', '089-654321'),
('Finanzen', 'laura.klein@muenchen.de', '2024-03-01', 'Laura Klein', '089-777888');

-- ===========================
-- NACHWUCHSKRAFT
-- ===========================
INSERT INTO NACHWUCHSKRAFT
(JAHRGANG, EMAIL, ERSTELLT_AM, NACHNAME, PERSONALNUMMER, STUDIENRICHTUNG, VORNAME)
VALUES
('2022/2025', 'max.mustermann@bwi.de', '2024-01-05 09:00:00', 'Mustermann', 'P1001', 'Wirtschaftsinformatik', 'Max'),
('2023/2026', 'sophie.meier@bwi.de', '2024-02-12 10:00:00', 'Meier', 'P1002', 'Informatik', 'Sophie'),
('2021/2024', 'leon.schneider@bwi.de', '2024-03-18 11:00:00', 'Schneider', 'P1003', 'BWL', 'Leon'),
('2024/2027', 'lena.huber@bwi.de', '2024-04-22 12:00:00', 'Huber', 'P1004', 'Verwaltungsinformatik', 'Lena');

-- ===========================
-- ABTEILUNG
-- ===========================
INSERT INTO ABTEILUNG (NAME) VALUES
('IT@M - IT-Dienstleistungen'),
('IT-Architektur'),
('Finanzen'),
('Kreisverwaltungsreferat - Bürgerdienste'),
('Kommunalreferat - Stadtplanung');

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
INSERT INTO STELLE (BESCHREIBUNG, BEWERBUNGSFRIST, STANDORT, STATUS, TITEL, SERVICEBEREICHSLEITER_ID)
VALUES
('Entwicklung und Betreuung interner Tools', '2024-12-31', 'München', 'OFFEN', 'Softwareentwickler (m/w/d)', 1),
('Mitarbeit im Personalcontrolling', '2024-11-30', 'München', 'GESCHLOSSEN', 'HR Analyst (m/w/d)', 2),
('Bearbeitung von Haushaltsanträgen', '2024-10-31', 'München', 'GESCHLOSSEN', 'Sachbearbeiter Finanzen', 3),
('Unterstützung IT-Support', '2024-12-15', 'München', 'OFFEN', 'IT-Support Assistent (m/w/d)', 1);

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
('Stadtplanung');

-- ===========================
-- STELLE_TAG
-- ===========================
INSERT INTO STELLE_TAG (STELLE_ID, TAG_ID) VALUES
(1, 1),
(1, 2),
(1, 7),
(2, 3),
(2, 4),
(3, 5),
(4, 6),
(4, 7);

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
(1, 1), (1, 2), (1, 4),
(2, 3), (2, 5), (2, 8),
(3, 6), (3, 5),
(4, 7), (4, 4), (4, 3);

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