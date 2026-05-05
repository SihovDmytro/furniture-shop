# External Integrations

## Core Sections (Required)

### 1) Integration Inventory

| System | Type | Purpose | Auth model | Criticality | Evidence |
|--------|------|---------|------------|-------------|----------|
| MySQL 8 | Relational DB | All persistent data (users, products, orders, categories) | Username/password (datasource config) | High | `application.yml`, `pom.xml` |
| Spring Security | In-process auth framework | Login, logout, route protection, ban tracking | Form-based login; `User` implements `UserDetails` | High | `SecurityConfig.java`, `FailureHandler.java` |
| Spring Boot Actuator | HTTP management endpoints | Health checks, info, metrics | All endpoints exposed (`include: "*"`); no auth guard observed | Medium | `application.yml` |
| Filesystem (avatars) | Local file I/O | User avatar image storage | None (local path) | Low | `AvatarRepositoryImpl.java`, `application.yml` |

### 2) Data Stores

| Store | Role | Access layer | Key risk | Evidence |
|-------|------|--------------|----------|----------|
| MySQL (`furniture_shop`) | Primary application database | Spring Data JPA repositories + `ProductRepositoryCriteriaImpl` | Credentials committed in plain YAML; no connection-pool tuning observed | `application.yml`, `pom.xml` |
| HTTP Session (`HttpSession`) | Cart state (`ShoppingCart`) | `CartService`, session attribute `Attributes.CART` | Non-distributable without sticky sessions or session replication | `entity/ShoppingCart.java`, `CartService.java` |
| Local filesystem | Avatar images | `AvatarRepositoryImpl` writes to `src/main/resources/static/avatars/` | Files lost on container redeploy; not volume-mounted in docker-compose | `AvatarRepositoryImpl.java`, `docker-compose.yml` |

### 3) Secrets and Credentials Handling

- **Credential sources:** Hardcoded in `src/main/resources/application.yml` (`username: root`, `password: admin`).
- **Hardcoding check:** Credentials are committed in plain text — no `.env`, no secrets manager, no Spring Cloud Config.
- **Rotation/lifecycle:** [ASK USER] — no documented rotation process.

### 4) Reliability and Failure Behavior

- **Retry/backoff:** None observed; datasource is a single Spring Boot HikariCP default pool — if MySQL is unreachable at startup the app fails to start.
- **Timeout policy:** Default Hikari connection timeout (30 s); no explicit override in config.
- **Circuit-breaker / fallback:** None.

### 5) Observability for Integrations

- **Logging:** `spring.jpa.show-sql: true` logs all SQL to console. Controller/service entry/exit logged via `log.trace` / `log.info(Constants.LOGGER_FORMAT, …)`.
- **Metrics/tracing:** Actuator endpoints exposed but no tracing agent (no Zipkin, no OpenTelemetry config) observed.
- **Missing visibility:** No structured logging, no correlation IDs, no alerting.

### 6) Evidence

- `src/main/resources/application.yml`
- `src/main/java/com/springtraining/furnitureshop/config/SecurityConfig.java`
- `src/main/java/com/springtraining/furnitureshop/repository/impl/AvatarRepositoryImpl.java`
- `src/main/java/com/springtraining/furnitureshop/entity/ShoppingCart.java`
- `docker-compose.yml`

