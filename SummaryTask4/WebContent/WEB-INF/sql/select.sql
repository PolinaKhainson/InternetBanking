CONNECT 'jdbc:derby://localhost:1527/sumtask4db;create=true;user=test;password=test';

----------------------------------------------------------------
-- test database:
----------------------------------------------------------------

SELECT * FROM credit_cards;
SELECT * FROM accounts;
SELECT * FROM credit_cards_accounts;
SELECT * FROM payments;
SELECT * FROM payment_statuses;
SELECT * FROM lock_statuses;
SELECT * FROM users;
SELECT * FROM roles;

DISCONNECT;

