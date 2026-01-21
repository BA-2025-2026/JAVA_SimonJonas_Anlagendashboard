use java_simonjonas_anlagendashboard_db;

# Dieses Skript importiert alle Daten der Datenbank
# aus CSV-Tabellen, die im Ordner C:\Daten\CSV liegen müssen.
# Die Importe müssen in der hier vorgegebenen Reihenfolge ausgeführt
# werden, da ansonsten die Foreign Key Constraints einen Fehler werfen.

# Daten für Tabelle "nutzer"
load data infile 'C:\\Daten\\CSV\\nutzer.csv'
    into table user
    character set latin1
    fields terminated by ','
    (ID_User, Email, FirstName, LastName, Password);


# Daten für Tabelle "broker"
load data infile 'C:\\Daten\\CSV\\broker.csv'
    into table broker
    fields terminated by ','
    (ID_Broker, Name);


# Daten für Tabelle "waehrung"
load data infile 'C:\\Daten\\CSV\\waehrung.csv'
    into table currency
    fields terminated by ','
    (ID_Currency, Name, Symbol);


# Daten für Tabelle "masseinheit"
load data infile 'C:\\Daten\\CSV\\masseinheit.csv'
    into table unit
    fields terminated by ','
    (ID_Unit, Name, Symbol);


# Daten für Tabelle "anlagetyp"
load data infile 'C:\\Daten\\CSV\\anlagetyp.csv'
    into table assettype
    fields terminated by ','
    (ID_AssetType, Name);

# Daten für Tabelle "anlage"
load data infile 'C:\\Daten\\CSV\\anlage.csv'
    into table asset
    fields terminated by ','
    (ID_Asset, Currency_ID, AssetType_ID, Unit_ID, Name, Symbol);

# Daten für Tabelle "kurs"
load data infile 'C:\\Daten\\CSV\\kurs.csv'
    into table price
    fields terminated by ','
    (ID_Price, Asset_ID, Timestamp, Price);


# Daten für Tabelle "nutzer_anlage"
LOAD DATA INFILE 'C:\\Daten\\CSV\\nutzer_anlage.csv'
    INTO TABLE user_asset
    FIELDS TERMINATED BY ','
    (ID_User_Asset, User_ID, Asset_ID, Broker_ID, Quantity, PurchasedAt, @vSoldAt)
    SET SoldAt = NULLIF(@vSoldAt, '');

# Check Impors
select *
from user_asset;

select *
from user;