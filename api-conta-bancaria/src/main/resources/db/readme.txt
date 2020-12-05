Essa duas pastas db-MySQL e db-PostgreSQL possui dentro os comando sql e o aplication.properties,
já devidamente configurados para esses bancos de dados, caso deseje alterar para algum deles,
copiei o aplication.properties e mude o user e password com as suas próprias configurações e cole no aplication.properties da pasta raiz db para a alteração ser feita.

Depois de ter feito isso, copie os arquivos de migração de tabelas exemplos :
001__criando-tabela-cliente.sql 

coloque ele na pasta migration e renomeie somente lá dentro, V001__criando-tabela-cliente.sql  colocando o V  na frente , para que o flyway possa executar os comandos sql em sequência correta.
