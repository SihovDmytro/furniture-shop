# Technology Stack

## Core Sections (Required)

### 1) Runtime Summary

| Area | Value | Evidence |
|------|-------|----------|
| Primary language | Java 11 | `pom.xml` → `<java.version>11</java.version>` |
| Runtime + version | JVM 11, Spring Boot 2.7.8 | `pom.xml` → `<parent>` block |
| Package manager | Maven | `pom.xml` |
| Module/build system | Maven (no wrapper; run `mvn` directly) | `pom.xml` |

### 2) Production Frameworks and Dependencies

| Dependency | Version | Role in system | Evidence |
|------------|---------|----------------|----------|
| spring-boot-starter-web | 2.7.8 (BOM) | MVC dispatcher, REST endpoints | `pom.xml` |
| spring-boot-starter-thymeleaf | 2.7.8 (BOM) | Server-side HTML templating | `pom.xml` |
| thymeleaf-extras-springsecurity5 | BOM | Thymeleaf `sec:` dialect for security in templates | `pom.xml` |
| spring-boot-starter-security | 2.7.8 (BOM) | Authentication, authorisation, session management | `pom.xml` |
| spring-boot-starter-data-jpa | 2.7.8 (BOM) | ORM / Spring Data repositories | `pom.xml` |
| hibernate-jpamodelgen | BOM | JPA2 static metamodel generation (used in Criteria queries) | `pom.xml` |
| spring-boot-starter-validation | 2.7.8 (BOM) | Bean Validation (JSR-380) on DTOs | `pom.xml` |
| spring-boot-starter-actuator | 2.7.8 (BOM) | Ops endpoints (health, info, …) | `pom.xml` |
| mysql-connector-j | BOM | JDBC driver for MySQL 8 | `pom.xml` |
| lombok | BOM | Boilerplate reduction (`@Data`, `@Slf4j`, …) | `pom.xml` |
| spring-boot-configuration-processor | BOM | `@ConfigurationProperties` metadata | `pom.xml` |
| spring-boot-devtools | BOM (runtime scope) | Live reload in development | `pom.xml` |

### 3) Development Toolchain

| Tool | Purpose | Evidence |
|------|---------|----------|
| Maven | Build, test, dependency management | `pom.xml` |
| spring-boot-starter-test (JUnit 5 + Mockito) | Unit & integration tests | `pom.xml` |
| spring-boot-devtools | Hot reload during development | `pom.xml` |
| Docker + Docker Compose | Container packaging and local stack | `Dockerfile`, `docker-compose.yml` |
| jQuery (CDN/vendor) | Frontend interactivity | `src/main/resources/static/vendor/` |

### 4) Key Commands

```bash
# Build (no wrapper — Maven must be on PATH)
mvn clean package

# Run locally (requires MySQL at localhost:3306/furniture_shop)
mvn spring-boot:run

# Run all tests (requires MySQL at localhost:3306/furniture_shop_test)
mvn clean test

# Docker-based run
docker-compose up --build
```

### 5) Environment and Config

- Config sources: `src/main/resources/application.yml` (default), `src/main/resources/application-docker.yml` (Docker profile), `src/test/resources/application.yml` (test)
- Required runtime values (hardcoded in `application.yml`, override for production):
  - `spring.datasource.url` — default `jdbc:mysql://localhost:3306/furniture_shop`
  - `spring.datasource.username` / `spring.datasource.password` — default `root` / `admin`
  - `furnitureshop.avatar.directory` — default `avatars/`
- No `.env` template found; credentials are currently committed in plain YAML — **[ASK USER]** whether a secrets management strategy is planned.
- Deployment constraint: app expects MySQL 8 to be reachable before startup (no retry logic observed).

### 6) Evidence

- `pom.xml`
- `src/main/resources/application.yml`
- `src/main/resources/application-docker.yml`
- `Dockerfile`
- `docker-compose.yml`

