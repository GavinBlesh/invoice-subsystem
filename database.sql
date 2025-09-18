-- Computer Science II
-- Assignment 4.0
-- Database
--
-- Name: Gavin Blesh

drop table if exists InvoiceItem;
drop table if exists Item;
drop table if exists Invoice;
drop table if exists Email;
drop table if exists Company;
drop table if exists Address;
drop table if exists Person;


create table Person (
	personId int not null auto_increment primary key,
	personUUID varchar(36) not null,
	firstName varchar(100) not null,
	lastName varchar(100) not null,
	phoneNumber varchar(20) not null
)engine=InnoDB,collate=latin1_general_cs;

create table Email(
	emailId int not null primary key auto_increment,
    personId int not null,
    email varchar(100) not null,
    foreign key (personId) references Person(personId)
)engine=InnoDB,collate=latin1_general_cs;

create table Address (
	addressId int not null primary key auto_increment,
	street varchar(50) not null,
    city varchar(50) not null,
    state varchar(50) not null,
    zipCode varchar(10) not null
)engine=InnoDB,collate=latin1_general_cs;

create table Company (
	companyId int not null auto_increment primary key,
	companyUUID varchar(36) not null unique key,
    name varchar(100) not null,
    contactId int not null,
    addressId int,
    foreign key (addressId) references Address(addressId),
    foreign key (contactId) references Person(personId)
)engine=InnoDB,collate=latin1_general_cs;

create table Invoice (
	invoiceId int not null auto_increment primary key,
	invoiceUUID varchar(36) not null unique key,
    date varchar(10) not null,
    customerId int not null,
    salesPersonId int not null,
    foreign key (customerId) references Company(companyId),
    foreign key (salesPersonId) references Person(personId)
)engine=InnoDB,collate=latin1_general_cs;

create table Item(
	itemId int not null auto_increment primary key,
	itemUUID varchar(36) not null unique key,
    name varchar(100) not null,
    itemType varchar(1) not null, -- Designates if item is Equipment, Contract, or Material
    servicerId int, -- Contract
    unit varchar(50), -- material 
    pricePerUnit double, -- material (stored in dollar value($0.00) format, rounds to the nearest cent.
    modelNumber varchar(50), -- Equipment
    retailCost double, -- Equipment (stored in dollar value ($0.00) format, rounds to the nearest cent.
    foreign key (servicerId) references Company(companyId),
    constraint itemType check (
    (itemType = 'E' and modelNumber is not null and retailCost is not null)
    or 
    (itemType = 'C' and servicerId is not null)
    or 
    (itemType = 'M' and unit is not null and pricePerUnit is not null)
    )
)engine=InnoDB,collate=latin1_general_cs;

create table InvoiceItem(
	invoiceItemId int not null primary key auto_increment,
    invoiceId int not null,
    itemId int not null,
    purchaseType varchar(1), -- For Equipment, designates if its Rental, Lease, or Purchase
    quantity int, -- For Material
    amount int, -- For Contract
    hoursRented double, -- For Rental
    startDate varchar(10), -- For Lease
    endDate varchar(10), -- For Lease
	foreign key (invoiceId) references Invoice(invoiceId),
    foreign key (itemId) references Item(itemId),
    constraint itemsType check (
		(purchaseType = 'R' and hoursRented is not null)
        or
        (purchaseType = 'L' and startDate is not null and endDate is not null)
        or
        (purchaseType = 'P')
        or
        (purchaseType is null and amount is not null)
        or
        (purchaseType is null and quantity is not null)
	)
)engine=InnoDB,collate=latin1_general_cs;

    
-- Inserting Data

-- Person and Email data
insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('2dd6ba52-0088-41c8-ae88-bf973ef851bf', 'Todd', 'Blesh', '402-949-5843');

insert into Email (personId, email) values 
(1,'blesh@gmail.com'),
(1,'ToddTheMan@coolsville.com'),
(1,'ToddBlesh@huskers.unl.edu'),
(1,'BleshT@mcuedu.nu');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('9d089b3c-ceab-4097-8958-537bf8fd1f54', 'Bridgette', 'Gerrie', '779-697-5503');
insert into Email (personId, email) values
(2,'bgerrie0@posterous.com');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('52f75456-370c-4a8e-9c38-fc248d7aa790', 'Johanna', 'McCaughan', '608-936-1917');
insert into Email (personId, email) values
(3,'jmccaughan1@posterous.com');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('cc1b8246-22a4-42f5-971e-bc2eecbc2f88', 'Rutledge', 'Penright', '801-668-7583');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('6cf3cda3-20e5-463d-897d-1e8480dd5a86', 'Kingsley', 'Reditt', '873-856-9533');
insert into Email (personId, email) values
(5,'kreditt3@cargocollective.com');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('d6d18210-43c4-4658-878f-a1e2538ad198','Mahalia','Tresler','284-518-6113');
insert into Email (personId, email) values
(6,'mtresler4@jimdo.com');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('90a46b27-19e4-4a6b-9b81-c07dfbabc2e9','Phillis','Ramble','963-858-6790');
insert into Email (personId, email) values
(7,'pramble5@amazon.de');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('8e8a58dd-5631-447d-992b-5585d22d5c47','Fallon','Barbier','816-606-8850');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('16b71f78-27b1-47a3-a370-cfe1721aee96','Melva','Abrahami','948-352-7284');
insert into Email (personId, email) values
(9,'mabrahami7@list-manage.com');

insert into Person (personUUID, firstName, lastName, phoneNumber)
values ('b98dbd4d-216d-44ba-95dc-7136b8321659','Cordy','Nelthorp','904-621-1294');
insert into Email (personId, email) values
(10,'cnelthorp8@foxnews.com');

-- Address Data
INSERT INTO Address (street, city, state, zipCode) VALUES ('Monterey', 'Krajan Bakalan', 'AR', '68046');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Superior', 'Wenquan', 'NY', '42904');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Mesta', 'Iowa City', 'IA', '52245');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Meadow Vale', 'Tongle', 'NH', '94034');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Gina', 'Jajce', 'VT', '92035');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Victoria', 'Semiluki', 'PA', '39693');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Summer Ridge', 'Medeiros Neto', 'RI', '45960');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Cordelia', 'Stare Pole', 'AZ', '82220');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Sage', 'Hangbu', 'CO', '68523');
INSERT INTO Address (street, city, state, zipCode) VALUES ('Madison', 'Fort Calhoun', 'NE', '68023');

-- Company Data
INSERT INTO Company (companyUUID, contactId, name, addressID) VALUES ('aef94300-01ec-4b20-8992-6a0c806f103e', 3, 'Gleason LLC', 1);
INSERT INTO Company (companyUUID, contactId,name, addressID) VALUES ('16ac68de-d8a2-4b23-a26e-1c1e2588a273', 4,'Kautzer-Toy', 2);
INSERT INTO Company (companyUUID, contactId, name, addressID) VALUES ('831e1dc1-e250-49ab-8e28-1b01a96cb173', 2,'Grimes Inc', 3);
INSERT INTO Company (companyUUID, contactId, name, addressID) VALUES ('501daacf-ea9b-49da-9e2e-ad8912922287', 5,'Baumbach and Sons', 4);
INSERT INTO Company (companyUUID, contactId, name, addressID) VALUES ('6e862c9e-0889-4110-8e95-dfdc8a2e0998', 6,'Weissnat Group', 5);
INSERT INTO Company (companyUUID, contactId, name, addressID) VALUES ('89f6f04d-902a-4e89-ade8-3503189ac8ad', 7,'Sanford-Ankunding', 6);
INSERT INTO Company (companyUUID, contactId,name, addressID) VALUES ('14eaa43b-ef72-4655-bf4e-687da18a33be', 8,'Hauck-Berge and Moen', 7);
INSERT INTO Company (companyUUID, contactId,name, addressID) VALUES ('afa77fcb-6bb9-4958-bfcf-80a9ab25c693', 9,'Wisoky-Schamberger', 8);
INSERT INTO Company (companyUUID, contactId,name, addressID) VALUES ('e1e351b5-ca25-497c-a5c9-f11bf83a8796', 10,'Lebsack and Sons', 9);
INSERT INTO Company (companyUUID, contactId, name, addressID) VALUES ('88e99dc9-c6e6-4cd8-8e40-cba8a86fd24a', 1,'Pioneer Diesel', 10);

-- Invoice Data
insert into Invoice (invoiceUUID, customerId, salesPersonId, date)
values ('a0c9c107-74f3-4122-bad0-8e004ac0f35d',9,9,'2006-02-02');
insert into Invoice (invoiceUUID, customerId, salesPersonId, date)
values ('cfb8981d-422b-42aa-80bf-4f08d7ea700c',7,5,'1993-12-25');
insert into Invoice (invoiceUUID, customerId, salesPersonId, date)
values ('ee96b900-1c6a-424a-ba41-7cb9d0515c98',1,1,'2025-03-14');
insert into Invoice (invoiceUUID, customerId, salesPersonId, date)
values ('44f4f57b-11ed-4f9d-94d2-fcd92e490cdf',10,2,'1981-10-01');
insert into Invoice (invoiceUUID, customerId, salesPersonId, date)
values ('05a3b41d-ec58-4bba-9213-ca4137f19b1b',10,8,'2022-01-06');


-- Items Data

-- Items Data (Equipment Only)
insert into Item (itemUUID, name, itemType, modelNumber, retailCost) values ('c0041b53-ed22-4f24-bc2b-fedf1c5c7ad6','Flying Bulldozer','E','WH-1000XM4',250);
insert into Item (itemUUID, name, itemType, modelNumber, retailCost) values ('1633e92e-a764-4fc5-b9ac-3a3a09238498','Wheel Loaders','E','950H',1502340);
insert into Item (itemUUID, name, itemType, modelNumber, retailCost) values ('fc2798b0-8ee6-43f7-a1cf-da943e33318c','Forklift','E','SH-957403',423598222);
insert into Item (itemUUID, name, itemType, modelNumber, retailCost) values ('943c0380-5759-4a63-9414-ae4fb0acc66f','Pogo Stick','E','FH94913',2953);

-- Items Data (Materials Only)
insert into Item (itemUUID, name, itemType, unit, pricePerUnit) values ('5756f72e-e87c-4c02-ae07-1208e6acf9df','batteries','M','box',23);
insert into Item (itemUUID, name, itemType, unit, pricePerUnit) values ('40e8b9f7-a35e-4723-8ce5-2b4617391e8d','steel','M','sheet',5043923);
insert into Item (itemUUID, name, itemType, unit, pricePerUnit) values ('7912a383-d8ba-43ec-bbe0-d27472582670','clay','M','box',2);
insert into Item (itemUUID, name, itemType, unit, pricePerUnit) values ('e93256e9-e17d-48ce-a214-3acfc6d26dcd','glass','M','pane',1);

-- Items Data (Contracts Only)
insert into Item (itemUUID, name, itemType, servicerId) values ('b8b0a00b-88d7-41bd-8c83-f914ca8b9cc2','plumbing installation','C', 3);
insert into Item (itemUUID, name, itemType, servicerId) values ('59b0cf42-d707-468a-be38-06ba9992f622','floor deinstallation','C', 4);
insert into Item (itemUUID, name, itemType, servicerId) values ('69b7fb02-a402-123f-bc99-02fc6312a0b0','pogo stick repair','C',10);
insert into Item (itemUUID, name, itemType, servicerId) values ('02000093-0030-4f2f-bec1-02f040345670','roof repair','C', 10);

-- InvoiceItems Data

-- InvoiceItems Data (Rental Only)
insert into InvoiceItem (InvoiceId, itemId, purchaseType, hoursRented) values (3, 2, 'R',24);
insert into InvoiceItem (InvoiceId, itemId, purchaseType, hoursRented) values (4, 2, 'R',15.4);
 
-- InvoiceItems Data (Lease Only)
insert into InvoiceItem (invoiceId, itemId, purchaseType, startDate, endDate) values (2, 1, 'L','1993-12-25', '1994-01-02');
insert into InvoiceItem (invoiceId, itemId, purchaseType, startDate, endDate) values (1, 4, 'L','2006-02-02','2010-05-05');

-- InvoiceItems Data (Purchase Only)
insert into InvoiceItem (itemId, invoiceId, purchaseType) values (3, 4, 'P');
insert into InvoiceItem (itemId, invoiceId, purchaseType) values (1, 1, 'P');

-- InvoiceItems Data (Materials Only)
insert into InvoiceItem (itemId, invoiceId, quantity) values (7, 2, 42);
insert into InvoiceItem (itemId, invoiceId, quantity) values (8, 5, 5);
insert into InvoiceItem (itemId, invoiceId, quantity) values (5, 1, 1520);
insert into InvoiceItem (itemId, invoiceId, quantity) values (6, 4, 3);

-- InvoiceItems Data (Contracts Only)
insert into InvoiceItem (itemId, invoiceId, amount) values (11,  4, 13023);
insert into InvoiceItem (itemId, invoiceId, amount) values (10,  1, 120530);
insert into InvoiceItem (itemId, invoiceId, amount) values (11, 5, 150);
insert into InvoiceItem (itemId, invoiceId, amount) values (12, 2, 120456);