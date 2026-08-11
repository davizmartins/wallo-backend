# Wallo API

API REST para controle financeiro pessoal. Permite que cada usuário gerencie suas contas, categorias e transações (receitas e despesas), com autenticação segura e dados agregados para dashboards.

Projeto desenvolvido para fins de estudo e portfólio, com foco em boas práticas de arquitetura, segurança e organização de código.

## Funcionalidades

- **Autenticação e autorização** com JWT e Spring Security (cadastro e login)
- **Gestão de contas** (carteira, conta corrente, poupança, investimento) com saldo em precisão monetária
- **Gestão de categorias** de receitas e despesas
- **Registro de transações** que movimentam automaticamente o saldo da conta associada
- **Dashboards** com dados agregados: total por categoria, total por tipo e evolução mensal
- **Isolamento por usuário**: cada usuário acessa apenas os próprios dados
- **Paginação** nas listagens
- **Tratamento centralizado de erros** com respostas padronizadas
- **Documentação interativa** via Swagger/OpenAPI

## Tecnologias

- **Java 25** e **Spring Boot 4.1**
- **Spring Security** + **JWT** (jjwt) para autenticação
- **Spring Data JPA** / Hibernate para persistência
- **PostgreSQL** como banco de dados
- **Bean Validation** para validação de entrada
- **springdoc-openapi** (Swagger) para documentação
- **Maven** para build e gestão de dependências

## Arquitetura

O projeto segue uma arquitetura em camadas, com separação clara de responsabilidades:

```
com.wallo.wallo_api
├── config/       Configurações (Spring Security, CORS, OpenAPI)
├── controller/   Endpoints REST
├── dto/          Objetos de transferência de dados (records)
├── enums/        Enumerações de domínio
├── exception/    Tratamento centralizado de exceções
├── model/        Entidades JPA
├── repository/   Acesso a dados (Spring Data JPA)
├── security/     Filtro JWT e integração com o Spring Security
└── service/      Regras de negócio
```

**Decisões de projeto relevantes:**

- Valores monetários em `BigDecimal` para evitar imprecisão de ponto flutuante
- Operações que alteram saldo são anotadas com `@Transactional`, garantindo atomicidade entre o registro da transação e a atualização da conta
- DTOs implementados como `record` (imutáveis) para entrada e saída
- Injeção de dependências via construtor
- Todas as consultas escopadas por usuário, garantindo isolamento de dados

## Como executar

### Pré-requisitos

- Java 25
- Maven
- PostgreSQL

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/davizmartins/wallo-backend.git
   ```

2. Crie o banco de dados no PostgreSQL:
   ```sql
   CREATE DATABASE wallo;
   ```

3. Configure o `application.properties`. Use o `application.properties.example` como base e preencha suas credenciais:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/wallo
   spring.datasource.username=SEU_USUARIO
   spring.datasource.password=SUA_SENHA
   jwt.secret=SUA_CHAVE_SECRETA_MINIMO_256_BITS
   jwt.expiration=86400000
   ```

4. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Acesse a documentação Swagger:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## Principais endpoints

A URL base é `http://localhost:8080`. Exceto os endpoints de autenticação, todos exigem um token JWT no cabeçalho `Authorization: Bearer <token>`.

### Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/register` | Cadastra um novo usuário |
| POST | `/auth/login` | Autentica e retorna o token JWT |

### Contas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/accounts` | Cria uma conta |
| GET | `/accounts` | Lista as contas (paginado) |
| PUT | `/accounts/{id}` | Atualiza uma conta |
| DELETE | `/accounts/{id}` | Remove uma conta |

### Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/categories` | Cria uma categoria |
| GET | `/categories` | Lista as categorias (paginado) |
| PUT | `/categories/{id}` | Atualiza uma categoria |
| DELETE | `/categories/{id}` | Remove uma categoria |

### Transações

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/transactions` | Registra uma transação e atualiza o saldo |
| GET | `/transactions` | Lista as transações (paginado) |
| DELETE | `/transactions/{id}` | Remove uma transação e reverte o saldo |

### Dashboard

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/dashboard/by-category` | Total por categoria em um período |
| GET | `/dashboard/total` | Total por tipo (receita/despesa) em um período |
| GET | `/dashboard/monthly` | Evolução mensal por tipo |

## Status do projeto

Backend funcional e completo em seus recursos principais. Próximos passos: frontend em React e melhorias como filtros avançados e cobertura de testes.

---

Desenvolvido por [Davi Martins](https://github.com/davizmartins).
