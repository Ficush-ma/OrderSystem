CREATE DATABASE IF NOT EXISTS OrderSystem;

USE OrderSystem;

CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      user_name VARCHAR(10) NOT NULL UNIQUE,
                      password VARCHAR(16) NOT NULL,
                      balance INT NOT NULL,
                      created_at DATETIME
);

CREATE TABLE product (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(10) NOT NULL UNIQUE,
                         stock INT NOT NULL,
                         price INT NOT NULL,
                         version INT NOT NULL,
                         created_at DATETIME
);

CREATE TABLE orderRecord (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             user_id INT NOT NULL,
                             product_id INT NOT NULL,
                             quantity INT NOT NULL,
                             amount INT NOT NULL,
                             status INT NOT NULL,
                             created_at DATETIME
);
INSERT INTO product (name, stock, price, version, created_at) VALUES ("显示器", 20, 300, 1, NOW());
INSERT INTO product (name, stock, price, version, created_at) VALUES ("音响", 30, 50, 1, NOW());
INSERT INTO product (name, stock, price, version, created_at) VALUES ("鼠标", 100, 20, 1, NOW());


