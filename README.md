# REST API with Spring Boot + PostgreSQL for employee and river cargo management

---

## Table of Contents

* [About the Project](#about-the-project)
* [Technologies Used](#technologies-used)
* [Package Structure](#package-structure)
* [How to Run the Application](#how-to-run-the-application)
* [Expected JSON Requests and Responses](#expected-json-requests-and-responses)
* [Permissions and Endpoints](#permissions-and-endpoints)
* [Resource Usage Management with Prometheus](#resource-usage-management-with-prometheus)
* [Final Remarks and Insights](#final-remarks-and-insights)

---

### About the Project

This project was initially developed for real-world use by a river cargo company in Brazil. Unfortunately, the company decided not to proceed with the digital migration, but the experience was highly educational, especially regarding security principles such as:

1. JWT-based token authentication.
2. Encrypted credential authentication using **BCrypt**.
3. Authorization systems with role validation via **OAuth2**.
4. API encapsulation with **Spring Security** to protect external requests.
5. Strengthened understanding of layered architecture.
6. Deployment with **AWS**. Even though it wasn’t completed, I had to learn it due to its real deployment potential (see more in the "Final Remarks and Insights").

---

### Technologies Used

* Java 17
* Spring Boot Framework
* Spring Security (OAuth2, BCrypt, JWT + RSA keys)
* Lombok
* PostgreSQL
* Docker + Docker-compose
* Prometheus

---

### Package Structure

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

---

### How to Run the Application

#### Requirements:

* Docker
* Docker-compose

#### Cloning repository:
In a empty directory, open terminal and digit:
```bash
  git clone https://github.com/art-83/spring-security-crud-restAPI
```

> \[ WARNING ]
> Remember to generate a `.env` file at the project root to configure environment variables, and generate your own RSA keys.

#### Creating a `.env` file:

```env
# Fill in with your preferences.

SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_JPA_HIBERNATE_DDL_AUTO=

POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
```

#### Generating `RSA` Keys:

Generate two RSA keys (2048 bits), one public and one private. Remember that the **public key** is derived from the **private key**, as they are connected for encryption/decryption purposes.
They must follow this format:

```plaintext
-----BEGIN PRIVATE KEY-----
// your private key here
-----END PRIVATE KEY-----
```

```plaintext
-----BEGIN PUBLIC KEY-----
// your public key here
-----END PUBLIC KEY-----
```

Then rename your **private key** to `jwt.rsa.priv` and your **public key** to `jwt.rsa.pub`.
Place both in the `src/main/resources` directory. This is important because the `application.properties` references depend on both keys being there.

#### Running the Application:

From the application directory, open a terminal and type:

```bash
  mvn clean package               # Build the app into a .jar
  wdocker-compose build           # Build docker-compose using the Dockerfile
  docker-compose up               # Start the containers
```

---

### Expected JSON Requests and Responses

To make testing easier, here's a list of expected JSON formats. You can also see the DTOs in `br/devdeloop/uepb/dtos`:

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

#### Ship Container Request:

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

> \[**IMPORTANT**]
> All enum values must exactly match the values in the application.
> *Example*: "role": "*DEVELOPER*"
> Since the **AppUserEnum** has roles written like `DEVELOPER`, the app is **case-sensitive**, so make sure to match the **Enum** values exactly.

---

### Permissions and Endpoints

The app has a role-based authorization system. By default, there's a pre-registered `DEVELOPER` user: `username = admin`, `password = admin`. Use this on `/auth/login` to obtain a **Token** for validating future requests. Each token is valid for 1 hour. The `/auth/login` endpoint is open (does not require a token). See the permissions table:

#### Permissions:

| ROLE          | ALLOWED METHODS | ENDPOINT ACCESS                            |
| ------------- | --------------- | ------------------------------------------ |
| N/A           | `POST`          | `/auth/**`                                 |
| *`DEVELOPER`* | `GET`, `POST`   | `/dev/**` + full access to other endpoints |
| *`CONSULTER`* | `GET`           | `/consult/**`                              |
| *`CREW`*      | `POST`          | `/crew/**`                                 |

#### Endpoints:

| ENDPOINT              | HTTP METHOD | DESCRIPTION                                |
| --------------------- | ----------- | ------------------------------------------ |
| `/auth/login`         | `POST`      | Authenticates user and returns JWT token   |
| `/dev/register`       | `POST`      | Registers a new user                       |
| `/dev/update`         | `POST`      | Updates an existing user's data            |
| `/crew/add-container` | `POST`      | Adds or updates a container                |
| `/consult/id/{id}`    | `GET`       | Returns data of a specific container by ID |
| `/consult/all-data`   | `GET`       | Returns all registered container data      |

---

### Resource Usage Management with Prometheus

I implemented `Prometheus` to monitor system resources and support decisions regarding deployment viability.

#### How to Use (Linux Only):

In the project folder, go to the `/prometheus` directory. Open a terminal and type:

```bash
  cd prometheus
```

Then unzip the Prometheus package:

```bash
  unzip prometheus.zip
```

Start Prometheus with:

```bash
  ./start.sh
```

It will run on port 9090 and collect logs from the `/actuator/**` endpoint, which can be translated into graphs.
Access statistics at: [Prometheus Queries](http://localhost:9090/query).

Add custom queries to get specific metrics:

| Metric           | Query                   | Description                  |
| ---------------- | ----------------------- | ---------------------------- |
| JVM Memory Usage | `jvm_memory_used_bytes` | JVM memory currently in use  |
| CPU Usage        | `system_cpu_usage`      | CPU usage of the application |

To stop Prometheus, run the following script from the `/prometheus` directory:

```bash
  ./stop.sh
```

---

### Final Remarks and Insights

This was a highly educational project. I'm proud to have created a working prototype with real deployment potential. One interesting note: the `docker-compose.yml` file contains very strict hardware limits. It was designed to run on **AWS Lightsail** using the cheapest plan: **512MB RAM** and **1 CPU**, aiming to support **3 containers** (only 2 are currently defined).

I used `Prometheus` to test load and resource usage. I allocated **260MB** for the API. The `JVM` used about **140MB–170MB**, leaving **212MB** for PostgreSQL and **40MB** for NGINX (not yet included). Since few requests were expected and the API includes `Spring Security`, load would be minimal. The main limitation would be CPU—sharing **1 thread** between **3 containers** is tough, but I believe it would work in a controlled, low-traffic environment.

I had planned more endpoints (e.g., for removing containers), but since the project didn't move forward, I saw no reason to keep refining it. It's not about proving I *can* do it—I already know that. In the end, I’m grateful for the experience and everything I learned.
