# Codebase Structure

## Core Sections (Required)

### 1) Top-Level Map

| Path | Purpose | Evidence |
|------|---------|----------|
| `src/main/java/…/furnitureshop/` | All application source code | directory tree |
| `src/main/resources/templates/` | Thymeleaf HTML templates | `templates/` listing |
| `src/main/resources/static/` | CSS, JS, images, vendor libs | `static/` listing |
| `src/main/resources/application.yml` | Runtime configuration (default profile) | file |
| `src/main/resources/application-docker.yml` | Docker profile overrides | file |
| `src/main/resources/messages*.properties` | i18n message bundles (en, ru, uk, default) | `resources/` listing |
| `src/test/java/…/` | Test classes (mirrors main package) | directory tree |
| `src/test/resources/application.yml` | Test-specific datasource config | file |
| `Dump20240523.sql` | MySQL schema + seed data | file |
| `pom.xml` | Maven build descriptor | file |
| `Dockerfile` | Docker image definition | file |
| `docker-compose.yml` | Multi-container local stack | file |
| `docs/codebase/` | Codebase documentation (this folder) | file |

### 2) Entry Points

- **Main runtime entry:** `src/main/java/com/springtraining/furnitureshop/FurnitureShopApplication.java` — standard `@SpringBootApplication` + `main()`
- **Secondary entry points:** None (single deployable unit, no workers or CLI)
- **Docker entry:** `Dockerfile` `ENTRYPOINT` runs the compiled JAR; `docker-compose.yml` wires MySQL service

### 3) Module Boundaries

| Boundary | What belongs here | What must not be here |
|----------|-------------------|------------------------|
| `controller/` | HTTP request handling, model population, redirect logic | Business logic, direct repository calls |
| `service/` | Transaction boundaries, orchestration between repositories | HTTP-layer concerns (HttpServletRequest, Model) |
| `repository/` | Spring Data interfaces, Criteria API implementations | Business rules, HTTP concerns |
| `domain/` | JPA `@Entity` classes and their static metamodels | Request/response shapes, service logic |
| `entity/` | Request DTOs, response POJOs, session-state objects (e.g. `ShoppingCart`) | JPA annotations, persistence logic |
| `security/` | Spring Security handlers (`FailureHandler`, `SuccessHandler`) | General business logic |
| `config/` | `@Configuration` beans (`SecurityConfig`, `LocalizationConfig`, `UserConfig`) | Feature logic |
| `captcha/` | Captcha strategy interface + implementations | Unrelated auth concerns |
| `util/` | String constants (`Views`, `Attributes`, `Parameters`, `Constants`) and `@ConfigurationProperties` `*Props` classes | Logic of any kind |
| `exceptions/` | Custom exception types | Handlers (those belong in controllers or config) |

### 4) Naming and Organization Rules

- **File naming:** PascalCase Java classes; e.g. `ProductController.java`, `OrderService.java`
- **Template naming:** camelCase HTML; e.g. `homePage.html`, `errorPage.html`
- **JS naming:** camelCase; e.g. `productsPage.js`, `ordersPage.js`, `cart.js`
- **Directory organization:** layered (controller / service / repository / domain) rather than feature-based
- **Constants:** string literals are collected in `util/Views.java`, `util/Attributes.java`, `util/Parameters.java`, `util/Constants.java` — never inline
- **Config properties:** `@ConfigurationProperties` beans are in `util/*Props.java` (`ProductProps`, `OrdersProps`, `PaginationProps`, `UserProps`, `AvatarProps`, `LocalizationProps`)
- **Import aliasing:** none; standard Java fully-qualified imports

### 5) Evidence

- `src/main/java/com/springtraining/furnitureshop/` (directory listing)
- `src/main/java/com/springtraining/furnitureshop/FurnitureShopApplication.java`
- `src/main/java/com/springtraining/furnitureshop/util/Views.java`
- `src/main/resources/templates/` (directory listing)

