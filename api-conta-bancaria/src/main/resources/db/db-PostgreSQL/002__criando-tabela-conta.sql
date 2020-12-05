create table conta(
id BigInt SERIAL PRIMARY KEY,
cliente_id BigInt not null,
saldo decimal(10,2) not null,
tipo_conta varchar(30) not null,
status_conta varchar(30) not null
) default charset=utf8, engine =InnoDB;

alter table conta  add constraint fk_conta 
foreign key(cliente_id) references cliente (id);