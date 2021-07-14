use `security_jdbc`;

CREATE TABLE `MyTable` (
	`client_id` VARCHAR(100),
	`password` VARCHAR(100),
	`is_active` BOOLEAN,
	`location` VARCHAR(100)
);
#primary key - client_id
INSERT INTO mytable (client_id,password,is_active,location) VALUES ("yashas","yashas1",true,"bangalore");
INSERT INTO mytable (client_id,password,is_active,location) VALUES ("malathi","malathi`",true,"Mangalore");
INSERT INTO mytable (client_id,password,is_active,location) VALUES ("krishna","krishna1",true,"Mangalore");

CREATE TABLE `permissions` (
	`client_id` VARCHAR(100),
	`role` VARCHAR(100)
);
#fk- client_id --> MyTable.client_id
create unique index ix_auth_username on permissions (client_id,role);

INSERT INTO permissions (client_id,role) VALUES ("krishna","ROLE_USER");



select * from mytable;
SELECT * FROM permissions;
