-- ===========================
-- SERVICEBEREICHSLEITER
-- ===========================
INSERT INTO SERVICEBEREICHSLEITER (ID, BEREICH, EMAIL, ERSTELLT_AM, KONTAKTPERSON, TELEFONNUMMER)
VALUES
(1, 'IT-Infrastruktur', 'anna.schmidt@muenchen.de', '2024-01-10', 'Anna Schmidt', '089-123456'),
(2, 'Personalmanagement', 'thomas.mueller@muenchen.de', '2024-02-14', 'Thomas Müller', '089-654321'),
(3, 'Finanzen', 'laura.klein@muenchen.de', '2024-03-01', 'Laura Klein', '089-777888');

-- ===========================
-- NACHWUCHSKRAFT
-- ===========================
INSERT INTO NACHWUCHSKRAFT
(ID, JAHRGANG, EMAIL, ERSTELLT_AM, NACHNAME, PERSONALNUMMER, STUDIENRICHTUNG, VORNAME, INTERESSEN, PRAKTIKA)
VALUES
(1, '2022/2025', 'max.mustermann@bwi.de', '2024-01-05T09:00:00', 'Mustermann', 'P1001',
 'Wirtschaftsinformatik', 'Max',
 'IT-Architektur, Cloud, Java',
 'IT@M - IT-Dienstleistungen'),
(2, '2023/2026', 'sophie.meier@bwi.de', '2024-02-12T10:00:00', 'Meier', 'P1002',
 'Informatik', 'Sophie',
 'Frontend, UX, E-Government',
 'Kommunalreferat - Stadtplanung'),
(3, '2021/2024', 'leon.schneider@bwi.de', '2024-03-18T11:00:00', 'Schneider', 'P1003',
 'BWL', 'Leon', 'Finanzen, Controlling', 'Finanzen'),
(4, '2024/2027', 'lena.huber@bwi.de', '2024-04-22T12:00:00', 'Huber', 'P1004',
 'Verwaltungsinformatik', 'Lena',
 'Bürgerdienste, Prozessdigitalisierung',
 'Kreisverwaltungsreferat - Bürgerdienste');

INSERT INTO ABTEILUNG (ID, NAME) VALUES
(1, 'IT@M - IT-Dienstleistungen'),
(2, 'IT-Architektur'),
(3, 'Finanzen'),
(4, 'Kreisverwaltungsreferat - Bürgerdienste'),
(5, 'Kommunalreferat - Stadtplanung');

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
INSERT INTO STELLE (ID, BESCHREIBUNG, BEWERBUNGSFRIST, STANDORT, STATUS, TITEL, SERVICEBEREICHSLEITER_ID)
VALUES
(1, 'Entwicklung und Betreuung interner Tools', '2024-12-31', 'München', 'OFFEN', 'Softwareentwickler (m/w/d)', 1),
(2, 'Mitarbeit im Personalcontrolling', '2024-11-30', 'München', 'GESCHLOSSEN', 'HR Analyst (m/w/d)', 2),
(3, 'Bearbeitung von Haushaltsanträgen', '2024-10-31', 'München', 'GESCHLOSSEN', 'Sachbearbeiter Finanzen', 3),
(4, 'Unterstützung IT-Support', '2024-12-15', 'München', 'OFFEN', 'IT-Support Assistent (m/w/d)', 1);

-- ===========================
-- TAG
-- ===========================
INSERT INTO TAG (ID, NAME)
VALUES
(1, 'Java'),
(2, 'Spring Boot'),
(3, 'HR'),
(4, 'Controlling'),
(5, 'Finanzen'),
(6, 'Support'),
(7, 'IT');

-- ===========================
-- STELLE_TAG
-- ===========================
INSERT INTO STELLE_TAG (STELLE_ID, TAG_ID)
VALUES
-- Softwareentwickler (m/w/d)
(1, 1),
(1, 2),
(1, 7),

-- HR Analyst (m/w/d)
(2, 3),
(2, 4),

-- Sachbearbeiter Finanzen
(3, 5),

-- IT-Support Assistent (m/w/d)
(4, 6),
(4, 7);

-- ===========================
-- BEWERBUNG
-- ===========================
INSERT INTO BEWERBUNG (ID, EINGEREICHT_AM, KOMMENTAR, STATUS, NACHWUCHSKRAFT_ID, STELLE_ID)
VALUES
(1, '2024-05-20', 'Ich freue mich auf diese spannende Position!', 'EINGEREICHT', 1, 1),
(2, '2024-06-15', 'Meine Kenntnisse passen sehr gut zur Stelle.', 'EINGEREICHT', 2, 2),
(3, '2024-07-10', 'Habe bereits Erfahrung im Bereich Controlling.', 'EINGEREICHT', 3, 2),
(4, '2024-08-02', 'Motiviert und bereit für neue Aufgaben.', 'EINGEREICHT', 4, 4);

-- ===========================
-- NACHWUCHSKRAFT_ANHANG
-- ===========================
INSERT INTO NACHWUCHSKRAFT_ANHANG (ID, DATEIPFAD, HOCHGELADEN_AM, TYP, NACHWUCHSKRAFT_ID)
VALUES
(1, '/uploads/lebenslauf_max.pdf', '2024-05-10', 'Lebenslauf', 1),
(2, '/uploads/zeugnis_sophie.pdf', '2024-06-02', 'Zeugnis', 2),
(3, '/uploads/motivation_leon.pdf', '2024-06-20', 'Motivationsschreiben', 3),
(4, '/uploads/lebenslauf_lena.pdf', '2024-07-05', 'Lebenslauf', 4);

-- ===========================
-- STELLENMERKLISTE
-- ===========================
INSERT INTO STELLENMERKLISTE (ERSTELLT_AM, MERKEN_STATUS, NACHWUCHSKRAFT_ID, STELLE_ID)
VALUES
('2024-06-01', TRUE, 1, 1),
('2024-06-05', TRUE, 2, 2),
('2024-06-10', FALSE, 3, 3),
('2024-07-01', TRUE, 4, 4);
