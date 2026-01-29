drop database if exists java_simonjonas_anlagendashboard_db;
create database java_simonjonas_anlagendashboard_db;
use java_simonjonas_anlagendashboard_db;

create table user (
                        ID_User int primary key auto_increment,
                        Email varchar(100) unique not null,
                        FirstName varchar(50) not null,
                        LastName varchar(50) not null,
                        Password varchar(150) not null
);

create table broker (
                        ID_Broker int primary key auto_increment,
                        Name varchar(52) unique not null
);

create table assettype (
                           ID_AssetType int primary key auto_increment,
                           Name varchar(51) unique not null
);

create table unit (
                             ID_Unit int primary key auto_increment,
                             Name varchar(21) unique not null,
                             Symbol varchar(11) unique not null
);

create table currency (
                          ID_Currency int primary key auto_increment,
                          Name varchar(71) unique not null,
                          Symbol varchar(11) unique not null
);


create table asset (
                        ID_Asset int primary key auto_increment,
                        Currency_ID int not null,
                        AssetType_ID int not null,
                        Unit_ID int not null,
                        Name varchar(201) not null,
                        Symbol varchar(21) unique,
                        foreign key (Currency_ID) references currency (ID_Currency)
                            on delete no action on update cascade,
                        foreign key (AssetType_ID) references assettype (ID_AssetType)
                            on delete no action on update cascade,
                        foreign key (Unit_ID) references unit (ID_Unit)
                            on delete no action on update cascade
);

create table price (
                      ID_Price bigint primary key auto_increment,
                      Asset_ID int not null,
                      Timestamp datetime not null,
                      Price decimal(12,4) not null,
                      foreign key (Asset_ID) references asset (ID_Asset)
                          on delete cascade on update cascade
);

create table user_asset (
                               ID_User_Asset int primary key auto_increment,
                               User_ID int not null,
                               Asset_ID int not null,
                               Broker_ID int,
                               Quantity decimal(21,10) not null,
                               PurchasedAt datetime not null,
                               SoldAt datetime,
                               foreign key (User_ID) references user (ID_User)
                                   on delete cascade on update cascade,
                               foreign key (Asset_ID) references asset (ID_Asset)
                                   on delete no action on update cascade,
                               foreign key (Broker_ID) references broker (ID_Broker)
                                   on delete no action on update cascade,
                               check ( PurchasedAt IS NULL OR PurchasedAt < SoldAt)
);