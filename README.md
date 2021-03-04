<h2 align="center">
  <p>api conta bancária</p>
</h2>

<h2>🧾 Sobre</h2>
<p>Através dessa api será possivel criar uma conta fictícia, consultar o saldo, fazer transferencia e fazer depósito.</p>

<h3>Projeto no Heroku</h3>
<a href="https://api-conta-bancaria.herokuapp.com/swagger-ui.html">Documentação do projeto</a>

<h2>🔧 Ferramentas</h2>
<ul>
    <li>
    <a href="https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html">Java 11</a>
    </li>
    <li>
    <a href="https://spring.io/projects/spring-boot">Spring Boot</a>
    </li>
    <li>
    <a href="https://spring.io/projects/spring-data-jpa">Spring Data JPA</a>
    </li>
    <li>
    <a href="https://beanvalidation.org/">Jakarta Bean Validation</a>
    </li>
    <li>
    <a href="https://hibernate.org/validator/documentation/getting-started/">Hibernate Validator</a>
    </li>
    <li>
    <a href="https://flywaydb.org/">flyway Migration</a>
    </li>
    <li>
    <a href="https://www.postgresql.org/">PostgreSQL</a>
    </li>
    <li>
    <a href="https://www.docker.com/">Docker</a>
    </li> 
    <li>
    <a href="https://swagger.io/">Swagger</a>
    </li>       
</ul>

<h2>👨‍💻 Como configurar</h2>
```bash
  # Clone o projeto
  $ git clone https://github.com/joaovitor-cbc/api-conta-bancaria
  #Execute o docker-compose para subir o postgres e o pgAdmin
  $ docker-compose up -d
```
<p>Você pode pegar todos os arquivos deste repositório e executá-los no seu computador como se estivessem online, apenas na sua máquina.</a>
<p>Dicas de acesso</p>
<ul>
    <li><a href="http://localhost:8080/swagger-ui.html">Documentação do Swagger</a>
    </li>
    <li>Configurações de acesso ao banco-> </br>
        <i>[ host=<b>localhost:5432</b>, usuario=<b>postgres</b>, senha=<b>admin</b>, database=<b>olimpiabank</b> ]</i>
    </li>
    <li><a href="htto://localhost:6543">PgAdmin</a> </br>
        <i>[Usuario= <b>admin@admin.com</b>,Senha= <b>admin</b>] </i>
    </li>
</ul>
## Diagrama
![Diagrama de caso de uso](diagrama.png)


<p align="center">Feito por<a href="https://www.linkedin.com/in/joão-vitor-araujo"> João Vitor</a></p>
