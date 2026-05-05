# Coding Conventions

## Core Sections (Required)

### 1) Naming Rules

| Item | Rule | Example | Evidence |
|------|------|---------|----------|
| Java classes | PascalCase | `ProductController`, `OrderService` | `controller/`, `service/` |
| Methods | camelCase | `getProductsPage()`, `createOrder()` | `ProductController.java` |
| Constants | `public static final String` in dedicated util class; UPPER_SNAKE_CASE for multi-word | `Views.HOME_PAGE`, `Constants.REDIRECT` | `util/Views.java`, `util/Constants.java` |
| JPA entities | PascalCase, table name in `@Table(name = "…")` | `User` → `@Table(name = "user")` | `domain/User.java` |
| Thymeleaf templates | camelCase `.html` | `homePage.html`, `errorPage.html` | `templates/` |
| JavaScript files | camelCase `.js` | `productsPage.js`, `ordersPage.js` | `static/js/` |
| Config properties beans | PascalCase + `Props` suffix | `ProductProps`, `PaginationProps` | `util/*Props.java` |
| Model attribute keys | camelCase string constants in `Attributes` | `Attributes.CART`, `Attributes.ORDERS` | `util/Attributes.java` |
| Request param names | lowercase camelCase string constants in `Parameters` | `Parameters.LOGIN`, `Parameters.ID` | `util/Parameters.java` |

### 2) Formatting and Linting

- **Formatter:** IntelliJ IDEA Eclipse Code Formatter config (`.idea/eclipseCodeFormatter.xml`) — no standalone formatter config in project root.
- **Linter:** None found in project root or `pom.xml`; no Checkstyle/PMD/SpotBugs plugin configured.
- **Enforced rules:** [TODO] — no automated enforcement; conventions are by team agreement.
- **Run commands:** [TODO] — no dedicated lint task.

### 3) Import and Module Conventions

- Standard Java fully-qualified imports; no aliasing.
- Lombok `@Slf4j` is used on controllers and services to get `log` without a field declaration.
- Imports are not alphabetized by tooling but are organized into `javax`, `org.springframework`, `com.springtraining` groups by convention.
- No barrel/re-export pattern (Java packages, not modules).

### 4) Error and Logging Conventions

- **Logging strategy:** Consistent two-call pattern in controllers and services:
  1. `log.trace("methodName start")` — method entry breadcrumb.
  2. `log.info(Constants.LOGGER_FORMAT, key, value)` — log significant values using the shared `"{}: {}"` format string.
- **Log level config:** `com.springtraining.furnitureshop` is set to `TRACE` in `application.yml`; all trace + info entries are visible by default.
- **Error handling:** Custom `ErrorController` renders `errorPage.html`. Validation errors are passed in session across redirects (see `RegistrationController`, `LoginController`).
- **Sensitive data:** No explicit redaction observed; passwords are BCrypt-hashed at the service layer but login credentials flow through standard Spring Security and are not logged.

### 5) Testing Conventions

- Test file placement: `src/test/java/…` mirroring the main package (`com.springtraining.furnitureshop.*Test.java`)
- Naming convention: `[ClassUnderTest]Test.java`
- Tests are `@SpringBootTest` integration tests; Mockito used for mocking collaborators
- Test datasource: `furniture_shop_test` DB (configured in `src/test/resources/application.yml`)

### 6) Evidence

- `src/main/java/com/springtraining/furnitureshop/util/Constants.java`
- `src/main/java/com/springtraining/furnitureshop/util/Views.java`
- `src/main/java/com/springtraining/furnitureshop/util/Attributes.java`
- `src/main/java/com/springtraining/furnitureshop/controller/ProductController.java`
- `.idea/eclipseCodeFormatter.xml`
- `src/main/resources/application.yml`

