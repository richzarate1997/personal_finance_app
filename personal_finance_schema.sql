-- -----------------------------------------------------
-- Schema tasteful
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `finance_tracker`;
CREATE DATABASE `finance_tracker`;
USE `finance_tracker`;

CREATE TABLE User (
    id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(256) NOT NULL, -- This should be a hashed password
    email VARCHAR(100),
    PRIMARY KEY(id)
);

CREATE TABLE Category (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    type ENUM('EXPENSE', 'INCOME') NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Transaction (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type ENUM('EXPENSE', 'INCOME') NOT NULL,
    date DATE NOT NULL,
    category_id INT NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES User(id),
    FOREIGN KEY(category_id) REFERENCES Category(id)
);

CREATE TABLE RecurringTransaction (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type ENUM('BILL', 'PAYDAY') NOT NULL,
    category_id INT NOT NULL,
    description VARCHAR(255),
    recurrence ENUM('DAILY', 'WEEKLY', 'MONTHLY') NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES User(id),
    FOREIGN KEY(category_id) REFERENCES Category(id)
);



