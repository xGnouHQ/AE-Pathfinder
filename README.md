# 🌐 Webapplikation – Universitätsprojekt AE-Pathfinder

Dokumentation "Pathfinder - WebApplikation" in Rahmen einer Universitätsveranstaltung

## 🚦 Projekt Status & Branches

| Branch | Rolle / Beschreibung | CI Status (Build & Test) | Deploy Status (Server) |
| :--- | :--- | :---: | :---: |
| **test-poc-1** | 🚀 Aktuelles Testsystem | - | [![Deploy Test PoC](https://github.com/ae-pathfinder/AE-Pathfinder/actions/workflows/deploy-test-poc.yml/badge.svg)](https://github.com/ae-pathfinder/AE-Pathfinder/actions/workflows/deploy-test-poc.yml)| 
| **combined-frontend-backend-mock** | Mockup Frontend+Backend Combined | [![CI Check (Build & Test)](https://github.com/ae-pathfinder/AE-Pathfinder/actions/workflows/ci-check.yml/badge.svg?branch=combined-frontend-backend-mock)](https://github.com/ae-pathfinder/AE-Pathfinder/actions/workflows/ci-check.yml) | — |
| **Frontend-backend-verknuepfung-2-0** | Frontend+backend Verknppüpfung 2.0 | [![CI Check (Build & Test)](https://github.com/ae-pathfinder/AE-Pathfinder/actions/workflows/ci-check.yml/badge.svg?branch=Frontend-backend-verknuepfung-2-0)](https://github.com/ae-pathfinder/AE-Pathfinder/actions/workflows/ci-check.yml) | — |

## 📖 Projektübersicht
Dieses Projekt wurde im Rahmen eines Universitätsmodul "Anwendungsentwicklung" entwickelt.  
Ziel war es, eine moderne Webapplikation mit einer klaren Trennung von **Frontend** und **Backend** zu erstellen.  
Die Entwicklung erfolgte mit **IntelliJ IDEA** als zentrale IDE.

---
## Frontend
- **Vue.js** – reaktives JavaScript-Framework für die Benutzeroberfläche  
- **Vuetify** – UI-Komponentenbibliothek basierend auf Material Design  
- **Node.js** – Laufzeitumgebung für die Entwicklung und Ausführung des Frontends  
- **npm (Node Package Manager)** – Verwaltung von Abhängigkeiten und Ausführung von Build- & Dev-Skripten

## Backend
- **Spring Boot** – Java-Framework für REST-Backends
- **Maven** – Build- und Dependency-Management-Tool
- **Java** – Programmiersprache für Geschäftslogik und API-Implementierung
- **REST API** – Schnittstelle zwischen Frontend und Backend
- **DB** - H2 oder Postgres

Frontend kommuniziert per HTTP / REST API mit dem Backend
