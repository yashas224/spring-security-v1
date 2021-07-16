show databases;

create database security_jpa;
use `security_jpa`;

CREATE TABLE `client` (
	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`client_id` VARCHAR(100),
	`password` VARCHAR(100),
	`is_active` BOOLEAN,
	`location` VARCHAR(100)
);
INSERT INTO client (client_id,password,is_active,location) VALUES ("yashas","yashas1",true,"bangalore");
INSERT INTO client (client_id,password,is_active,location) VALUES ("malathi","malathi`",true,"Mangalore");
INSERT INTO client (client_id,password,is_active,location) VALUES ("krishna","krishna1",true,"Mangalore");

CREATE TABLE `permissions` (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`client_id` VARCHAR(100),
	`role` VARCHAR(100)
);
create unique index ix_auth_username on permissions (client_id,role);

INSERT INTO permissions (client_id,role) VALUES ("malathi","ROLE_USER");



select * from client;
SELECT * FROM permissions;
