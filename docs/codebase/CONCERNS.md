# Codebase Concerns

## Core Sections (Required)

### 1) Top Risks (Prioritized)

| Severity | Concern | Evidence | Impact | Suggested action |
|----------|---------|----------|--------|------------------|
| High | CSRF protection disabled | `SecurityConfig.java` `.csrf().disable()` | All state-changing endpoints are vulnerable to cross-site request forgery | Re-enable CSRF; use Spring's built-in token support with Thymeleaf's `th:action` |
| High | Credentials committed in plain text | `src/main/resources/application.yml` (`password: admin`) | DB credentials exposed in source control | Move to environment variables or a secrets manager; add to `.gitignore` |
| High | Actuator endpoints fully exposed without auth | `application.yml` `include: "*"` + no actuator security rule in `SecurityConfig` | `/actuator/env`, `/actuator/heapdump` etc. are unauthenticated | Restrict actuator to admin role or localhost only |
| Medium | Session-backed shopping cart not distributable | `entity/ShoppingCart.java`, `CartService.java` | Cannot run multiple app instances without sticky sessions or distributed session store | Use Redis/Spring Session or persist cart to DB |
| Medium | Avatar files lost on container restart | `AvatarRepositoryImpl.java` writes to `src/main/resources/static/avatars/`; no Docker volume in `docker-compose.yml` | User avatars disappear on redeploy | Mount a persistent volume; store avatars outside the JAR |
| Low | Order cancellation not implemented | `OrderController.java:84` `// TODO: 007` | Feature gap; UI may expose a dead code path | Implement or remove UI trigger |

### 2) Technical Debt

| Debt item | Why it exists | Where | Risk if ignored | Suggested fix |
|-----------|---------------|-------|-----------------|---------------|
| No code coverage tooling | Never configured | `pom.xml` | Regressions go undetected | Add JaCoCo plugin with minimum threshold |
| Repository test layer removed | Commit `4b41998` deleted tests | `src/test/…/repository/` | Silent regressions in complex Criteria queries | Restore or rewrite repository integration tests |
| No CI/CD pipeline | Project was started without one | Entire repo | Manual builds, no automated gate | Add GitHub Actions workflow for build + test |
| `@SpringBootTest` used for unit-level tests | All tests load full Spring context | `src/test/java/…/` | Slow test suite; DB required even for simple logic | Refactor pure-logic tests to plain JUnit + Mockito without context |
| Spring Boot 2.7.x (EOL) | Not upgraded | `pom.xml` | No security patches after EOL (Nov 2023) | Migrate to Spring Boot 3.x / Java 17+ |

### 3) Security Concerns

| Risk | OWASP category | Evidence | Current mitigation | Gap |
|------|---------------|----------|--------------------|-----|
| CSRF disabled | A01 Broken Access Control | `SecurityConfig.java` `.csrf().disable()` | None | Re-enable + use Thymeleaf CSRF tokens |
| Plain-text credentials in VCS | A02 Cryptographic Failures | `application.yml` | BCrypt on stored passwords | Externalize datasource credentials |
| Unauthenticated Actuator | A01 Broken Access Control | `application.yml`, `SecurityConfig.java` | None | Lock down actuator endpoints |
| Login brute-force ban stored in session | A07 Identification and Auth Failures | `FailureHandler.java` | Attempt counter + time-based ban | Ban state is per-session; a new session resets the counter |
| No HTTPS enforcement | A02 | `SecurityConfig.java` | None observed | Add `requiresChannel().anyRequest().requiresSecure()` |

### 4) Performance and Scaling Concerns

| Concern | Evidence | Current symptom | Scaling risk | Suggested improvement |
|---------|----------|-----------------|-------------|-----------------------|
| `show-sql: true` in all profiles | `application.yml` | Verbose SQL log output in production | Log volume, slight overhead | Disable or scope to `dev` profile only |
| No connection pool tuning | `application.yml` (no HikariCP overrides) | Default pool of 10 connections | Saturation under moderate load | Set `maximum-pool-size` and `minimum-idle` appropriate for workload |
| JPA Criteria query built dynamically each request | `ProductRepositoryCriteriaImpl` | [TODO] — not profiled | Potential N+1 or full-table scans without index | Add query cache or indexed columns on filter fields |

### 5) Fragile/High-Churn Areas

| Area | Why fragile | Churn signal | Safe change strategy |
|------|-------------|-------------|----------------------|
| `src/test/…/repository/impl/` | Tests were recently modified (churn) and the layer had tests deleted | 2 commits in last 90 days | Add tests before modifying; confirm schema matches seed SQL |
| `src/main/java/…/controller/OrderController.java` | Contains unimplemented TODO; used by both page and AJAX consumers | Active feature area | Integration-test all order endpoints before touching |
| `src/main/java/…/security/` | Login attempt ban logic is stateful (session-based); changes affect security posture | Part of most recent feature commits | Pair changes with `FailureHandlerTest` + manual login flow verification |

### 6) `[ASK USER]` Questions

1. **[ASK USER]** Is there a planned secrets management strategy (e.g. environment variables, Vault, AWS Secrets Manager) to replace the committed `application.yml` credentials?
2. **[ASK USER]** Should CSRF protection be re-enabled? The current `csrf().disable()` was likely done for AJAX convenience — are there plans to fix this?
3. **[ASK USER]** Is there a roadmap to upgrade from Spring Boot 2.7 (EOL) to Spring Boot 3.x / Java 17?
4. **[ASK USER]** What is the intended deployment target: single-instance VM, Docker Compose, Kubernetes? This affects the session-cart and avatar-storage strategies.
5. **[ASK USER]** Should Actuator endpoints be restricted (to admin role or localhost)? Currently all endpoints are fully public.

### 7) Evidence

- `.codebase-scan.txt` (TODO / FIXME / HACK section, HIGH-CHURN section)
- `src/main/java/com/springtraining/furnitureshop/config/SecurityConfig.java`
- `src/main/resources/application.yml`
- `src/main/java/com/springtraining/furnitureshop/controller/OrderController.java` (line 84)
- `src/main/java/com/springtraining/furnitureshop/repository/impl/AvatarRepositoryImpl.java`
- Git log: `4b41998 Remove repository tests`
- `pom.xml` (Spring Boot 2.7.8, no JaCoCo plugin)

