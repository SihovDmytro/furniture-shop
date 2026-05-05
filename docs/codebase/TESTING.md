# Testing Patterns

## Core Sections (Required)

### 1) Test Stack and Commands

- **Primary test framework:** JUnit 5 (via `spring-boot-starter-test` 2.7.8)
- **Assertion/mocking tools:** Mockito (bundled in `spring-boot-starter-test`), Spring MockMvc

```bash
# Run all tests (requires MySQL at localhost:3306/furniture_shop_test)
mvn clean test

# Run a single test class
mvn test -Dtest=OrderServiceTest

# No dedicated coverage command configured; add JaCoCo plugin to enable
```

### 2) Test Layout

- **Placement:** `src/test/java/com/springtraining/furnitureshop/` — mirrors main package structure exactly.
- **Naming convention:** `[ClassUnderTest]Test.java` (e.g. `OrderServiceTest.java`, `CartControllerTest.java`).
- **Setup files:** `src/test/resources/application.yml` overrides datasource to `furniture_shop_test` DB; no other test-specific config files observed.

### 3) Test Scope Matrix

| Scope | Covered? | Typical target | Notes |
|-------|----------|----------------|-------|
| Unit | Partial | Services, security handlers, captcha strategies | Most tests are `@SpringBootTest` with Mockito mocks rather than pure unit tests |
| Integration | Yes | Controllers (`CartControllerTest`, `OrderControllerTest`, `ProductControllerTest`), repositories (`ProductRepositoryCriteriaImplTest`, `AvatarRepositoryImplTest`) | Full Spring context; requires live test DB |
| E2E | No | — | No Selenium/Playwright test suite in repo |

### 4) Mocking and Isolation Strategy

- **Main mocking approach:** Mockito `@MockBean` / `@Mock` to isolate service or repository collaborators inside a `@SpringBootTest` context.
- **Isolation guarantees:** Test datasource uses a separate DB (`furniture_shop_test`); tests should provision/reset data manually — no embedded DB (H2) or `@Transactional` rollback strategy detected.
- **Common failure mode:** Tests fail if MySQL `furniture_shop_test` DB is not provisioned before the suite runs (no in-memory DB fallback).

### 5) Coverage and Quality Signals

- **Coverage tool + threshold:** None configured — no JaCoCo or similar plugin in `pom.xml`. [TODO] add coverage tooling.
- **Current reported coverage:** Unknown — no CI/CD pipeline generates reports.
- **Known gaps:** Repository tests were removed in commit `4b41998`; repository layer has reduced coverage. No E2E tests.

### 6) Evidence

- `src/test/java/com/springtraining/furnitureshop/` (directory listing)
- `src/test/resources/application.yml`
- `pom.xml` (`spring-boot-starter-test` dependency)
- Git log: `4b41998 Remove repository tests`
- `target/surefire-reports/` (test results from last run)

