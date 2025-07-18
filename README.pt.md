# API REST com Spring Boot + PostgreSQL para gerenciamento de funcionários e cargas fluviais.

---
## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Tecnologias usadas](#tecnologias-usadas)
- [Estrutura dos packages](#estrutura-dos-packages)
- [Como executar a aplicação](#como-executar-a-aplicação)
- [JSON Request esperados na API e Responses](#json-request-esperados-na-api-e-responses)
- [Permissões e endpoints](#permissões-e-endpoints)
- [Gerenciar uso de recursos com o Prometheus](#gerenciar-uso-de-recursos-com-o-prometheus)
- [Observações finais e curiosidades](#observações-finais-e-curiosidades)
---

### Sobre o projeto
Projeto inicialmente feito para uso real de uma empresa de cargas fluviais no brasil, infelizmente a empresa não quis testar a migração para o meio tecnológico, porém, serviu de muito aprendizado, principalmente sobre princípios de segurança, como: 
1. Tokens com **JWT**.
2. Autenticações de credenciais criptografadas com **BCrypt**.
3. Sistemas de autorização com validação de roles com **OAuth2**.
4. Encapsulamento da API com **Spring Security** para proteção de requests externos.
5. Aprimoramento de meu conhecimento sobre arquiteturas de camadas.
6. Deploy usando **AWS**, apesar de não ter ido pra frente, tive que aprender pois era uma possibilidade real (mais detalhes em Observações e curiosidades).

---

### Tecnologias usadas
- Java 17
- Spring Boot Framework
- Spring Security (OAuth2, BCrypt, JWT + RSA keys)
- Lombok
- PostgreSQL
- Docker + Docker-compose
- Prometheus

---

### Estrutura dos packages
br.devdeloop.uepb\
├── 🗀 controllers\
├── 🗀 dtos\
├── 🗀 exceptions\
├── 🗀 init\
├── 🗀 mappers\
├── 🗀 models\
├── 🗀 repositories\
├── 🗀 security\
├── 🗀 services\
├── 🗀 util\
└── ©️ UepbApiApplication

___

### Como executar a aplicação
#### Requisitos:
- Docker
- Docker-compose
- Maven

#### Clonando o repositório:
Em um diretório vazio, abra o terminal e digite:
```bash
  git clone https://github.com/art-83/spring-security-crud-restAPI
```

> [ AVISO ]
> Lembrar de gerar um `.env` na raiz do projeto para configurar as variáveis de ambiente e também gerar suas próprias RSA keys.

#### Gerando um `.env`:
```
# Preencha de acordo com sua preferência.

SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_JPA_HIBERNATE_DDL_AUTO=

POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
```

#### Executando:
No diretório da aplicação, abra o terminal e digite:
```bash
  mvn clean package               # Para buildar a aplicação em um .jar
  docker-compose build            # Para buildar o docker-compose usando o Dockerfile
  docker-compose up               # Para subir os containers
```
---

### JSON Request esperados na API e Responses
Para facilitar o trabalho de testes, vou especificar os JSON que são esperados no sistema, você também pode visualizar exatamente como é o **Data Transfer Object** no package `dtos` em `br/devdeloop/uepb/dtos`:
#### Register Request:
```json
{
  "username": "username",
  "password": "password",
  "role": "AppUserEnum"
}
```
#### Login Request:
```json
{
  "username": "username",
  "password": "password",
  "role": "AppUserEnum"
}
```
#### Ship Container Request
```json
{
  "id": "id",
  "pusher": "PusherEnum",
  "shipQuantity": "shipQuantity",
  "branchExitDateTime": "branchExitDateTime",
  "destinationArrivalDateTime": "destinationArrivalDateTime",
  "destinationExitDateTime": "destinationExitDateTime",
  "branchArrivalDateTime": "branchArrivalDateTime",
  "observation": "observation",
  "status": "StatusEnum"
}
```
> [ _**IMPORTANTE**_ ]
> Todos os valores quem tem 'Enum' devem corresponder exatamente ao valor do Enum na aplicação.\
> _Exemplo_: "role": "_DEVELOPER_"\
> Pois no **Enum** `AppUserEnum`, a role é escrita exatamente como: `DEVELOPER`, a aplicação é **case-sensitive**, então garanta que a **role** seja exatamente igual ao **Enum**.
---

### Permissões e endpoints
A aplicação tem um sistema de autorização por `ROLE`, por padrão, a aplicação vem com um `DEVELOPER` já cadastrado username = `admin`, password = `admin`, use-o em `/auth/login` para conseguir um **Token** para validar as próximas requisições, **cada Token é válido por 1h**, `/auth/login` não precisa de permissões para acessar, ou seja, **não precisa de Token no Header do request**, veja as permissões de acordo com essa tabela:

#### Permissões:

| ROLE          | PROTOCOLOS PERMITIDOS | ENDPOINTS PERMISSÕES                               |
|---------------|-----------------------|----------------------------------------------------|
| N/A           | `POST`                | `/auth/**`                                         |  
| _`DEVELOPER`_ | `GET`, `POST`         | `/dev/**` + Permissão em todos os outros ENDPOINTS |                           
| _`CONSULTER`_ | `GET`                 | `/consult/**`                                      |       
| _`CREW`_      | `POST`                | `/crew/**`                                         |  


#### Endpoints:
| ENDPOINT              | MÉTODO HTTP | DESCRIÇÃO                                           |
| --------------------- | ----------- |-----------------------------------------------------|
| `/auth/login`         | `POST`      | Autentica o usuário e retorna o token JWT           |
| `/dev/register`       | `POST`      | Registra um novo usuário no sistema                 |
| `/dev/update`         | `POST`      | Atualiza os dados de um usuário já existente        |
| `/crew/add-container` | `POST`      | Adiciona ou atualiza um container                   |
| `/consult/id/{id}`    | `GET`       | Retorna os dados de um container específico pelo ID |
| `/consult/all-data`   | `GET`       | Retorna todos os dados de containers cadastrados    |

---

### Tabela de erros HTTP retornáveis na API


| Código HTTP | Exceção                             | Mensagem Retornada                              | Descrição                                                                 |
|-------------|--------------------------------------|--------------------------------------------------|---------------------------------------------------------------------------|
| 400         | `BadRequestException`               | `Username already registered.`                  | Quando uma tentativa de registro é feita com um nome de usuário já existente. |
| 401         | `BadCredentialsException`           | `Bad credentials.`                              | Quando as credenciais fornecidas no login são inválidas.                  |
| 403         | `AccessDeniedException`             | `User role mismatch.`                           | Quando o usuário não possui permissão para acessar o recurso.            |
| 400         | `PersistenceException`              | `Failure trying to add/update an ship container.` | Erro ao tentar persistir ou atualizar informações de um container fluvial. |
| 400         | `NoSuchElementException`            | `Ship container not found.`                     | Quando um container fluvial solicitado não é encontrado no banco.        |
| 400         | `RuntimeException` (genérica)       | `Some shit happened. Go to see Java console.`   | Exceção genérica não tratada especificamente — verifique os logs.        |


---

### Gerenciar uso de recursos com o Prometheus
Implementei o `Prometheus` para o gerenciamento de recursos para tomar minhas decisões no deploy da aplicação caso ela fosse para frente.

#### Como usar (apenas Linux):
Na pasta do projeto, você vai encontrar o diretório `/prometheus`, para acessar, abra o terminal e digite:
```bash
  cd prometheus
```
Em seguida, descompacte o `.zip` do `Prometheus` digitando no terminal:
```bash
  unzip prometheus.zip
```
Depois execute o script usando:
```bash
  ./start.sh
```
Ele vai inicializar o `Prometheus` na porta 9090, que captura logs enviados no endpoint `/actuator/**` que podem ser lidos e traduzidos para gráficos.\
Você pode acessar as estatíscas em:
_[Prometheus Queries](http://localhost:9090/query)_.\
\
Adicione Queries personalizadas para pegar informações específicas da aplicação:

| Métrica                | Query                     | O que representa        |
|------------------------|---------------------------|-------------------------|
| Memória usada pela JVM | `jvm_memory_used_bytes `  | Memória usada pela JVM  |
| Uso de CPU             | ` system_cpu_usage `      | Uso de CPU na aplicação |

Lembre-se de encerrar o `Prometheus`, no mesmo diretório (`/prometheus`), abra o terminal e execute o script:
```bash
  ./stop.sh
```

---

### Observações finais e curiosidades
Foi um projeto de muito aprendizado, fico feliz que eu consegui fazer o protótipo de algo que teve a chance de rodar de verdade algum dia, uma curiosidade, no arquivo `docker-compose.yml`, vai ter limitações de hardware bem severas, pois ele foi projetado para rodar no `Lightsail` na **AWS**, com o plano mais barato, mas que supria a demanda esperada, **512mb** de ram e **1 CPU** para tentar rodar **3 containers** é um pouco ousado, o terceiro container seria para rodar `NGINX`, assim totalizando os **3 containers** (atualmente só tem 2).\
Fiz testes de carga usando o `Prometheus` para gerenciar o uso de recursos do projeto, reservei **240mb** para a API, a `JVM` usava entre **140mb** - **170mb**, sobrando **240mb** para o PostgreSQL e fica **32** não usados, que seria para o `NGINX`, e como iriam receber poucos request, e a API tem o `Spring Security` implementados, evitaria ao máximo a sobrecarga, mas o maior limitador seria o número de **CPU**, ter que dividir **1 thread** para **3 containers** seria um desafio, mas em um ambiente mais controlado e com poucas requisições no dia, com as medidas que tomei, acredito que seria possível.\
Planejava colocar mais endpoints, como algum para remover as cargas, mas como o projeto não foi pra frente, não vejo mais sentido em polir ele, e não é como se eu não soubesse fazer, só iria gastar mais tempo e para provar um conhecimento que eu sei que tenho, enfim, foi muito bom esse projeto.
