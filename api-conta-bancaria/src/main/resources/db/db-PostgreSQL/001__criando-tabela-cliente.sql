create table cliente(
id BigInt SERIAL PRIMARY KEY,
cpf varchar(14) not null,
unique key cpf(cpf)
)default charset=utf8, engine =InnoDB;