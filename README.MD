# Projeto: Uso de Transações no Spring

Este projeto é um exemplo prático de como gerenciar transações em uma aplicação Spring, demonstrando os conceitos de **ACID** e as diferentes estratégias de propagação de transações.

## Objetivo

O objetivo deste projeto é mostrar como as transações podem ser configuradas e gerenciadas em uma aplicação Spring, garantindo a integridade dos dados em operações complexas, como processamento de pedidos, atualização de estoque e validação de pagamentos.

---

## Funcionalidades Principais

1. **Processamento de Pedidos**:
    - Criação de pedidos com validação de estoque.
    - Atualização do estoque após a conclusão do pedido.
   

2. **Validação de Pagamento**:
    - Simulação de validação de pagamento.
    - Registro de logs de auditoria em caso de falha no pagamento.
   

3. **Logs de Auditoria**:
    - Registro de ações importantes, como sucesso ou falha no processamento de pedidos.
    - Uso de transações independentes (`REQUIRES_NEW`) para garantir que os logs sejam persistidos mesmo em caso de falha na transação principal.

## Estratégias de Propagação de Transações

O projeto demonstra o uso das seguintes estratégias de propagação de transações no Spring:

- **`REQUIRED`**: Participa de uma transação existente ou cria uma nova se não houver uma.
- **`REQUIRES_NEW`**: Sempre cria uma nova transação, suspendendo qualquer transação existente.
- **`NESTED`**: Executa dentro de uma transação aninhada, permitindo rollback independente.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**: Para criação da aplicação e gerenciamento de transações.
- **Spring Web**: Para desenvolvimento de APIs RESTful.
- **Spring Data JPA**: Para acesso ao banco de dados e operações de CRUD.
- **H2 Database**: Banco de dados em memória para simulação (pode ser substituído por MySQL, PostgreSQL, etc.).
- **SpringDoc OpenAPI**: Para documentação automática da API usando Swagger.

# Como Testar o Projeto

## 1. Acesso ao Banco de Dados H2
O projeto utiliza o H2 Database, um banco de dados em memória, para facilitar testes e desenvolvimento. Você pode acessar o console do H2 para visualizar e manipular os dados diretamente.

**URL do H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**Credenciais**:

- **Username**: sa
- **Password**: (deixe em branco)

## 2. Inserindo Dados Iniciais
Para testar o processamento de pedidos, você pode inserir alguns produtos no banco de dados. Execute o seguinte comando SQL no console do H2:

```sql
INSERT INTO PRODUCT (NAME, PRICE, STOCK_QUANTITY) VALUES
    ('Laptop', 25000.0, 10),
    ('Smartphone', 5000.0, 12),
    ('TV', 7000.0, 20);
```
Isso criará três produtos com estoque inicial, que podem ser usados para simular pedidos.

## Testando a API

### 1. Criar um Pedido
Use o endpoint `POST /api/orders` para criar um novo pedido. Você pode testar isso diretamente no Swagger.

**Acesse o Swagger**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

No Swagger, selecione o endpoint `POST /api/orders`, clique em "Try it out", e insira o seguinte JSON no corpo da requisição:

**Request**:

```json
{
  "productId": 1,
  "quantity": 2
}
```
**Reponse**:
```json
{
"id": 1,
"productId": 1,
"quantity": 2,
"totalPrice": 50000.0
}
```

## O que acontece:

- O pedido é criado.
- O estoque do produto é atualizado.
- Um log de auditoria é registrado.

### 2. Simular Falha no Pagamento
Se o valor total do pedido for maior que 10000, o método `validatePayment` lançará uma exceção, simulando uma falha no pagamento.

**Request**:

```json
{
  "productId": 3,
  "quantity": 19
}
```

## O que acontece:

- O pagamento falha.
- Um log de auditoria é registrado indicando a falha.
- A transação principal é revertida, e o pedido não é persistido.

### 3. Verificar Logs de Auditoria
Você pode consultar os logs de auditoria diretamente no banco de dados H2. Use a seguinte query:

```sql
SELECT * FROM AUDIT_LOG;
```
Isso mostrará todos os logs registrados, incluindo sucessos e falhas no processamento de pedidos.

## Exemplos de Uso

1. **Processamento de Pedido com Sucesso**  
   Um pedido é criado, o estoque é atualizado, e um log de auditoria é registrado.

2. **Falha na Validação de Pagamento**  
   Se o pagamento falhar, um log de auditoria é registrado e a transação principal é revertida.

3. **Estoque Insuficiente**  
   Se o estoque for insuficiente, uma exceção é lançada e a transação é revertida.

## Para Saber Mais

Se você quiser se aprofundar no uso de transações no Spring, confira este artigo do DevMedia:

[Conheça o Spring Transactional Annotations](https://www.devmedia.com.br/conheca-o-spring-transactional-annotations/32472)
