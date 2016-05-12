--==============================================================
-- SummaryTask4 DB creation script for Apache Derby
--==============================================================

-- connection ij to database sumtask4db, if it doesn't exist DBMS creates one
CONNECT 'jdbc:derby://localhost:1527/sumtask4db;create=true;user=test;password=test';

-- remove all tables from the database

DROP TABLE payments;
DROP TABLE payment_statuses;
DROP TABLE credit_cards_accounts;
DROP TABLE accounts;
DROP TABLE credit_cards;
DROP TABLE users;
DROP TABLE lock_statuses;
DROP TABLE roles;



----------------------------------------------------------------
-- ROLES
-- users roles
----------------------------------------------------------------
CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE) 	
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
----------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started 
-- from 0 with the step equaled to 1
----------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');


----------------------------------------------------------------
-- LOCK STATUSES
-- statuses for orders
----------------------------------------------------------------
CREATE TABLE lock_statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

----------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Status entity, so the numeration must started 
-- from 0 with the step equaled to 1
----------------------------------------------------------------
INSERT INTO lock_statuses VALUES(0, 'locked');
INSERT INTO lock_statuses VALUES(1, 'unlocked');
INSERT INTO lock_statuses VALUES(2, 'waiting for unlock');



----------------------------------------------------------------
-- USERS
----------------------------------------------------------------
CREATE TABLE users(

-- 'generated always AS identity' means id is autoincrement field 
-- (from 1 up to Integer.MAX_VALUE with the step 1)
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	lock_status_id INTEGER NOT NULL REFERENCES lock_statuses(id),
	
-- 'UNIQUE' means logins values should not be repeated in login column of table	
	login VARCHAR(10) NOT NULL UNIQUE,
	
-- not null string columns	
	password VARCHAR(10) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	email VARCHAR(320) NOT NULL,
	
-- this declaration contains the foreign key constraint	
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
	role_id INTEGER NOT NULL REFERENCES roles(id) 

-- removing a row with some ID from roles table implies removing 
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in 
-- users table with ROLES_ID=ID
		ON DELETE CASCADE 

-- the same as previous but updating is used insted deleting
		ON UPDATE RESTRICT
);

-- id = 1, lock_status_id = 1, login = admin, password = admin, 
--first_name = Ivan, last_name = Ivanov, email = admin@admin.com, role=0
INSERT INTO users VALUES(DEFAULT, 1, 'admin', 'admin', 'Ivan', 'Ivanov','admin@admin.com', 0);
-- id = 2, lock_status_id = 1, login = client, password = client, 
--first_name = Vasya, last_name = Vasin, email = vasya@vasin.com, role=1
INSERT INTO users VALUES(DEFAULT, 1, 'client', 'client', 'Vasya', 'Vasin','vasya@vasin.com', 1);
-- id = 3, lock_status_id = 1, login = петров, password = петров, 
--first_name = Петр, last_name = Петров,email = petr@petrov.com, role=1
INSERT INTO users VALUES(DEFAULT, 1, 'петров', 'петров', 'Петр', 'Петров','petr@petrov.com', 1);
-- id = 4, lock_status_id = 0, login = иванов, password = иванов, 
--first_name = Иван, last_name = Иванов,email = ivan@ivanov.com, role=1
INSERT INTO users VALUES(DEFAULT, 0, 'иванов', 'иванов', 'Иван', 'Иванов','ivan@ivanov.com', 1);


----------------------------------------------------------------
-- CREDIT CARDS
----------------------------------------------------------------
CREATE TABLE credit_cards(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	credit_card_number INTEGER NOT NULL,
	ending_date DATE NOT NULL
);

-- credit_card_id = 1; user_id = 2, credit_card_number = 12345678, ending_date = '2016-01-01';
INSERT INTO credit_cards VALUES(DEFAULT, 2, 12345678,'2016-01-01');
-- credit_card_id = 2; user_id = 2, credit_card_number = 87654321, ending_date = '2017-01-01';
INSERT INTO credit_cards VALUES(DEFAULT, 2, 87654321,'2017-01-01');
-- credit_card_id = 3; user_id = 3, credit_card_number = 12344321, ending_date = '2018-01-01';
INSERT INTO credit_cards VALUES(DEFAULT, 3, 12344321,'2018-01-01');
-- credit_card_id = 4; user_id = 3, credit_card_number = 43211234, ending_date = '2019-01-01';
INSERT INTO credit_cards VALUES(DEFAULT, 3, 43211234,'2019-01-01');
-- credit_card_id = 5; user_id = 3, credit_card_number = 88888888, ending_date = '2020-01-01';
INSERT INTO credit_cards VALUES(DEFAULT, 3, 88888888,'2020-01-01');


----------------------------------------------------------------
-- ACCOUNTS
----------------------------------------------------------------
CREATE TABLE accounts(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
	lock_status_id INTEGER NOT NULL REFERENCES lock_statuses(id) ON DELETE RESTRICT,
	account_number INTEGER NOT NULL,
	sum_on_account DECIMAL(20,2) NOT NULL
	
);

-- account_id = 1; user_id = 2; lock_status_id=1; account_number = 111111; sum_on_account=100;
INSERT INTO accounts VALUES(DEFAULT, 2, 1, 111111, 100.5);
-- account_id = 2; user_id = 2; lock_status_id=1; account_number = 222222; sum_on_account=200;
INSERT INTO accounts VALUES(DEFAULT, 2, 1,222222, 200.56);
-- account_id = 3; user_id = 3; lock_status_id=0; account_number = 333333; sum_on_account=300;
INSERT INTO accounts VALUES(DEFAULT, 3, 0,333333, 300.60);
-- account_id = 4; user_id = 3; lock_status_id=0; account_number = 444444; sum_on_account=400;
INSERT INTO accounts VALUES(DEFAULT, 3, 0, 444444, 400);
-- account_id = 5; user_id = 2; lock_status_id=0; account_number = 555555; sum_on_account=500;
INSERT INTO accounts VALUES(DEFAULT, 2, 0, 555555, 500);
-- account_id = 6; user_id = 2; lock_status_id=0; account_number = 666666; sum_on_account=600;
INSERT INTO accounts VALUES(DEFAULT, 2, 0, 666666, 600);
-- account_id = 7; user_id = 2; lock_status_id=0; account_number = 777777; sum_on_account=700;
INSERT INTO accounts VALUES(DEFAULT, 2, 0, 777777, 700);


----------------------------------------------------------------
-- CREDIT_CARDS_ACOUNTS
----------------------------------------------------------------
CREATE TABLE credit_cards_accounts(
	credit_cards_accounts_id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	credit_card_id INTEGER NOT NULL REFERENCES credit_cards(id) ON DELETE CASCADE,
	account_id INTEGER NOT NULL REFERENCES accounts(id) ON DELETE CASCADE
	);
-- credit_card_id = 1, accounts_id = 1;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 1, 1);
-- credit_card_id = 2, accounts_id = 2;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 2, 2);
-- credit_card_id = 3, accounts_id = 3;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 3, 3);
-- credit_card_id = 4, accounts_id = 4;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 4, 4);
-- credit_card_id = 5, accounts_id = 1;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 5, 1);
-- credit_card_id = 5, accounts_id = 1;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 5, 2);
-- credit_card_id = 2, accounts_id = 5;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 2, 5);
-- credit_card_id = 2, accounts_id = 6;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 2, 6);
-- credit_card_id = 2, accounts_id = 7;
INSERT INTO credit_cards_accounts VALUES(DEFAULT, 2, 7);
	


----------------------------------------------------------------
-- PAYMENT STATUSES
-- statuses for orders
----------------------------------------------------------------
CREATE TABLE payment_statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

----------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Status entity, so the numeration must started 
-- from 0 with the step equaled to 1
----------------------------------------------------------------
INSERT INTO payment_statuses VALUES(0, 'prepared');
INSERT INTO payment_statuses VALUES(1, 'confirmed');

----------------------------------------------------------------
-- PAYMENTS
----------------------------------------------------------------
CREATE TABLE payments(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	credit_card_id INTEGER NOT NULL REFERENCES credit_cards(id) ON DELETE CASCADE,
	account_id INTEGER NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
	status_id INTEGER NOT NULL REFERENCES payment_statuses(id),
	sum_of_money DECIMAL(20,2) NOT NULL DEFAULT 0,
	payment_date_time TIMESTAMP NOT NULL
	
);

-- credit_card_id = 1; account_id = 1; status_id=0 sum_of_money = 10, payment_date_time = '2016-01-01'; 
INSERT INTO payments VALUES(DEFAULT,1, 1, 0, 10, '2016-01-01 10:03:20');
-- credit_card_id = 2; account_id = 2; status_id=1 sum_of_money = 20, payment_date_time = '2016-02-02'; 
INSERT INTO payments VALUES(DEFAULT,2, 2, 1, 20, '2016-02-02 11:03:20');
-- credit_card_id = 3; account_id = 3; status_id=0 sum_of_money = 30, payment_date_time = '2016-03-03';  
INSERT INTO payments VALUES(DEFAULT,3, 3, 0, 30, '2015-03-03 12:03:20');
-- credit_card_id = 4; account_id = 4; status_id=1 sum_of_money = 40, payment_date_time = '2016-04-04';  
INSERT INTO payments VALUES(DEFAULT,4, 4, 1, 40,'2015-04-04 13:03:20');
-- credit_card_id = 1; account_id = 1; status_id=1 sum_of_money = 40, payment_date_time = '2016-04-04';  
INSERT INTO payments VALUES(DEFAULT,1, 1, 0, 13,'2015-06-04 13:03:20');
-- credit_card_id = 2; account_id = 2; status_id=1 sum_of_money = 25, payment_date_time = '2016-04-04';  
INSERT INTO payments VALUES(DEFAULT,2, 2, 1, 25,'2015-07-04 13:03:20');
-- credit_card_id = 3; account_id = 3; status_id=0 sum_of_money = 50, payment_date_time = '2016-04-04';  
INSERT INTO payments VALUES(DEFAULT,3, 3, 0, 50,'2015-08-04 13:03:20');
-- credit_card_id = 4; account_id = 4; status_id=0 sum_of_money = 60, payment_date_time = '2016-04-04';  
INSERT INTO payments VALUES(DEFAULT,4, 4, 0, 60,'2015-09-04 13:03:20');

  
DISCONNECT;

