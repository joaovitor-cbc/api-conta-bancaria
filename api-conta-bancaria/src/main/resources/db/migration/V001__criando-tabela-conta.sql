create table conta(
id BigInt not null auto_increment,
saldo decimal(10,2) not null,
tipo_conta varchar(30) not null,
status_conta varchar(30) not null,
primary key(id)
) default charset=utf8, engine =InnoDB;