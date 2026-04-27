# Anlagendashboard

Desktop-Applikation zur Analyse und Verwaltung eines Finanzportfolios. Benutzer können ihre offenen Positionen einsehen und Portfolio-Kennzahlen wie Gesamtwert, investiertes Kapital sowie absolute und relative Performance berechnen lassen.

## Technologie-Stack

| Technologie | Version |
|-------------|---------|
| Java | 21 (mit Preview-Features) |
| JavaFX | 21 |
| Maven | 3.x |
| MySQL | 8.x |
| JUnit Jupiter | 5.10.2 |
| Gson | 2.10.1 |

## Voraussetzungen

- Java 21+
- Maven 3.x
- MySQL-Datenbank (lokal)

## Einrichtung

### 1. Datenbank aufsetzen

```sql
-- Schema erstellen
source src/main/java/net/ictcampus/semodul/anlagendashboard/database/setup/CreateDatabase.sql

-- Beispieldaten importieren
source src/main/java/net/ictcampus/semodul/anlagendashboard/database/setup/ImportFromCSV.sql
```

### 2. Datenbankverbindung konfigurieren

Datei `src/main/resources/config.properties` erstellen:

```properties
db.url=jdbc:mysql://localhost:3306/java_simonjonas_anlagendashboard_db
db.user=DEIN_BENUTZER
db.password=DEIN_PASSWORT
```

### 3. Anwendung starten

```bash
mvn clean javafx:run
```

## Tests ausführen

```bash
mvn clean test
```

## Projektstruktur

```
src/main/java/.../anlagendashboard/
├── gui/
│   └── App.java                    # JavaFX-Einstiegspunkt
├── user/                           # Benutzerverwaltung
│   ├── UserModel / UserDto
│   ├── UserDao / UserJdbcDao       # DAO-Pattern
│   ├── UserService
│   └── UserController
├── portfoliometrics/               # Portfolio-Analyse
│   ├── PortfolioMetricsService     # Berechnungslogik
│   ├── PortfolioMetricsController
│   ├── UserAssetDao / PriceDao
│   └── PortfolioMetricsDto
├── database/
│   ├── ConnectionFactory           # Singleton DB-Verbindung
│   ├── DbProperties
│   └── setup/                      # SQL-Skripte & CSV-Daten
└── utility/
    ├── FinanceMathUtil             # Finanzberechnungen
    ├── JsonUtil / TimeUtil
    └── NoDataFoundException
```

## Berechnungslogik

| Kennzahl | Formel |
|----------|--------|
| Gesamtwert | Σ (Menge × aktueller Kurs) |
| Investiertes Kapital | Σ (Menge × Kaufkurs) |
| Absolute Performance | Gesamtwert − Investiertes Kapital |
| Relative Performance | (Gesamtwert − Investiertes Kapital) / Investiertes Kapital |

## Architektur

Das Projekt folgt einem klassischen Schichtenmodell:

```
GUI (JavaFX)
    ↓
Controller
    ↓
Service (Business Logic)
    ↓
DAO Interface
    ↓
JDBC Implementation → MySQL
```

**Design Patterns:** DAO, DTO, Service Layer, Singleton (ConnectionFactory), Dependency Injection
