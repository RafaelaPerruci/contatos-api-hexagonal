# Agenda de Contatos

API REST em **Spring Boot 3 + Java 21** para gerenciamento de contatos, construída com arquitetura em camadas, validação declarativa e busca genérica via Spring Data `Example`.

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.3.4 |
| Spring Data JPA | — |
| H2 (banco em memória) | — |
| Bean Validation (Jakarta) | — |
| Lombok | — |
| Maven | 3.6+ |

## Arquitetura

```
controller  →  service  →  repository
    ↕              ↕
  DTOs          Domain / Entity
    ↕
  Mapper
```

| Camada | Responsabilidade |
|---|---|
| `ContatoController` | Recebe requisições HTTP, delega ao serviço |
| `ContatoService` | Regras de negócio: criação, busca, atualização, remoção |
| `ContatoRepository` | Acesso ao banco via Spring Data JPA |
| `ContatoMapper` | Converte entre `ContatoRequestDTO` ↔ `ContatoDomain` ↔ `Contato` |
| `GlobalExceptionHandler` | Tratamento centralizado de erros (`@RestControllerAdvice`) |

## Como executar

**Pré-requisitos:** Java 21+ e Maven 3.6+

```bash
mvn spring-boot:run
```

- Aplicação: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2`

## Endpoints

| Método | URL | Descrição |
|--------|-----|-----------|
| `POST` | `/contatos/incluir` | Cria um novo contato |
| `GET` | `/contatos/listar` | Lista todos os contatos |
| `GET` | `/contatos/pesquisar?tipoBusca=<campo>&valor=<valor>` | Pesquisa por campo |
| `PUT` | `/contatos/editar/{id}` | Atualiza um contato existente |
| `DELETE` | `/contatos/excluir/{id}` | Remove um contato |
| `GET` | `/contatos/logs` | Retorna o total de contatos cadastrados |

### POST `/contatos/incluir`

```json
{
  "nome": "Maria Silva",
  "telefone": "11999998888",
  "email": "maria@exemplo.com",
  "endereco": "Rua A, 100",
  "idade": 30,
  "tipo": "AMIGO",
  "dataCadastro": "2026-05-24",
  "ativo": "S"
}
```

**Resposta (`200 OK`):**

```json
{
  "id": 1,
  "nome": "Maria Silva",
  "telefone": "11999998888",
  "email": "maria@exemplo.com"
}
```

### GET `/contatos/pesquisar`

Campos aceitos em `tipoBusca`:

| Campo | Tipo de correspondência |
|---|---|
| `nome` | Contém (case-insensitive) |
| `telefone` | Contém |
| `email` | Contém |
| `endereco` | Contém |
| `tipo` | Contém (ex.: `AMIGO`, `FAMILIA`, `TRABALHO`, `OUTRO`) |
| `dataCadastro` | Contém |
| `ativo` | Contém (ex.: `S`, `N`) |
| `idade` | Exato (numérico) |

**Exemplos:**

```
GET /contatos/pesquisar?tipoBusca=nome&valor=maria
GET /contatos/pesquisar?tipoBusca=tipo&valor=AMIGO
GET /contatos/pesquisar?tipoBusca=idade&valor=30
```

## Validações

O `ContatoRequestDTO` aplica as seguintes regras via Bean Validation:

| Campo | Regra |
|---|---|
| `nome` | Obrigatório, mínimo 3 caracteres |
| `telefone` | Obrigatório |
| `email` | Obrigatório, formato de e-mail válido |
| `endereco` | Obrigatório |
| `idade` | Obrigatório, entre 1 e 150 |
| `tipo` | Obrigatório |
| `dataCadastro` | Obrigatório (`LocalDate`) |
| `ativo` | Obrigatório |

## Erros

| Código | Situação |
|---|---|
| `400 Bad Request` | Validação falhou ou parâmetros inválidos |
| `404 Not Found` | Contato não encontrado pelo `id` |
| `409 Conflict` | E-mail já cadastrado |
| `204 No Content` | Exclusão realizada com sucesso |
