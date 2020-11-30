create table conta(
id BigInt not null auto_increment,
cliente_id BigInt not null,
saldo decimal(10,2) not null,
tipo_conta varchar(30) not null,
status_conta varchar(30) not null,
primary key(id)
) default charset=utf8, engine =InnoDB;

alter table conta  add constraint fk_conta 
foreign key(cliente_id) references cliente (id);