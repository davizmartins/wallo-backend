# Wallo — API

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Maven](https://img.shields.io/badge/Maven-red)
![JWT](https://img.shields.io/badge/Auth-JWT-purple)

API REST do **Wallo**, um sistema de controle financeiro pessoal. Permite ao usuário gerenciar contas, categorias e transações, com autenticação segura e dados agregados para dashboards.

> Interface web (React): [wallo-frontend](https://github.com/davizmartins/wallo-frontend)

## Funcionalidades

- Autenticação e cadastro de usuários com JWT (tokens stateless)
- Senhas criptografadas com BCrypt
- CRUD de categorias, contas e transações, isolados por usuário
- Movimentação automática de saldo ao registrar ou excluir transações
- Validação de saldo suficiente antes de registrar despesas
- Endpoints de agregação para dashboards (total por tipo, por categoria e evolução mensal)
- Tratamento centralizado de erros com respostas padronizadas
- Documentação interativa via Swagger/OpenAPI

## Tecnologias

- **Java 25** e **Spring Boot 4.1**
- **Spring Security** com autenticação JWT (jjwt)
- **Spring Data JPA** / Hibernate
- **PostgreSQL** como banco de dados
- **Maven** para build e dependências
- **springdoc-openapi** para documentação Swagger

## Arquitetura

O projeto segue uma organização em camadas:

- `controller` — endpoints REST
- `service` — regras de negócio
- `repository` — acesso a dados (Spring Data JPA)
- `model` — entidades JPA
- `dto` — objetos de transferência de dados (records)
- `security` — configuração de autenticação e filtro JWT
- `exception` — tratamento centralizado de erros
- `config` — configurações gerais (CORS, Swagger)

Valores monetários usam `BigDecimal` para precisão. A autenticação é stateless: cada requisição carrega o token JWT, sem sessão no servidor.

## Como executar localmente

### Pré-requisitos

- Java 25
- PostgreSQL 17
- Maven (ou o wrapper `./mvnw` incluído)

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/davizmartins/wallo-backend.git
   cd wallo-backend
   ```

2. Crie um banco de dados PostgreSQL chamado `wallo`.

3. Configure as credenciais. Copie o arquivo de exemplo e ajuste com seus dados:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```
   Edite `application.properties` com a URL, usuário e senha do seu PostgreSQL, além da chave secreta do JWT.

4. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

5. A API estará disponível em `http://localhost:8080`.

### Documentação da API

Com a aplicação rodando, acesse a documentação interativa em:

```
http://localhost:8080/swagger-ui.html
```

## Principais endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/register` | Cadastro de usuário |
| POST | `/auth/login` | Login (retorna token JWT) |
| GET/POST/PUT/DELETE | `/categories` | Gerenciamento de categorias |
| GET/POST/PUT/DELETE | `/accounts` | Gerenciamento de contas |
| GET/POST/DELETE | `/transactions` | Gerenciamento de transações |
| GET | `/dashboard/total` | Total por tipo e período |
| GET | `/dashboard/by-category` | Total agrupado por categoria |
| GET | `/dashboard/monthly` | Evolução mensal |

As rotas (exceto `/auth/**` e a documentação) exigem o token JWT no cabeçalho `Authorization: Bearer <token>`.

## Autor

Desenvolvido por [Davi Martins](https://github.com/davizmartins).
