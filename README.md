TOMCAT 10.1.34

pgJDBC 42.7.5

DB postgres

JDK - openJDK-21 version 21.0.1

## Local PostgreSQL in Docker

Start only PostgreSQL:

```bash
docker compose up -d postgres
```

The application is already configured to connect to:

```properties
jdbc:postgresql://localhost:5432/${DB_NAME}
```

Use these environment variables when running the Spring Boot app locally:

```bash
DB_USER=currency_user
DB_PASSWORD=currency_password
DB_NAME=currency_exchange
```

For example:

```bash
set -a
source .env.example
set +a
mvn spring-boot:run
```

If you need pgAdmin in Docker too:

```bash
docker compose --profile tools up -d
```

Open pgAdmin at `http://localhost:5050`, login with `admin@example.com` / `admin`.
When adding the database server in pgAdmin, use:

```text
Host: postgres
Port: 5432
Database: currency_exchange
Username: currency_user
Password: currency_password
```

If the Spring Boot app is moved into Docker later, change the JDBC host from
`localhost` to `postgres` inside the container network.
