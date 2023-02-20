#!/bin/bash
 
mysql -uroot -pbd2<<MYSQL_SCRIPT
DROP DATABASE IF EXISTS bd2_grupo17;
CREATE DATABASE bd2_grupo17;
DROP USER IF EXISTS 'grupo17'@'localhost';
CREATE USER 'grupo17'@'localhost' IDENTIFIED BY 'grupo17';
GRANT ALL PRIVILEGES ON bd2_grupo17.* TO 'grupo17'@'localhost';
FLUSH PRIVILEGES;
MYSQL_SCRIPT
