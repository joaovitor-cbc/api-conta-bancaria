create table cliente(
id BigInt not null auto_increment,
conta_id BigInt not null,
cpf varchar(14) not null,
primary key(id),
unique key cpf(cpf)
)default charset=utf8, engine =InnoDB;

alter table cliente  add constraint fk_cliente 
foreign key(conta_id) references conta (id);