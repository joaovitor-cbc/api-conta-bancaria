create table cliente(
id BigInt auto_increment,
cpf varchar(14) not null,
primary key(id),
unique key cpf(cpf)
)default charset=utf8, engine =InnoDB;