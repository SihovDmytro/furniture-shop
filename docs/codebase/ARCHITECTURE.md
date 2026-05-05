# Architecture

## Core Sections (Required)

### 1) Architectural Style

- **Primary style:** Classic layered MVC (Controller → Service → Repository → Domain)
- **Why this classification:** The codebase has explicit, non-overlapping `controller/`, `service/`, `repository/`, and `domain/` packages; controllers render Thymeleaf views and expose small JSON endpoints consumed by page-level JavaScript; business logic is in services; persistence is in repositories.
- **Primary constraints:**
  1. Session-backed cart state — `ShoppingCart` lives in `HttpSession` (`Attributes.CART`), which ties horizontal scalability to sticky sessions or session replication.
  2. Server-side rendering first — all pages are Thymeleaf templates; JavaScript is used only for AJAX enrichment (cart, order details, product filters).
  3. Single deployable unit — no microservices; MySQL is the only data store.

### 2) System Flow

```text
Browser HTTP request
  → Spring DispatcherServlet
    → @Controller (controller/)
      → @Service (service/) [transaction boundary]
        → @Repository (repository/ or repository/impl/)
          → MySQL via Hibernate/JPA
      ← domain entities / Page<T> / Optional<T>
    ← Model attributes populated; Thymeleaf renders HTML
  ← HTML response (or JSON for AJAX endpoints)
```

Key AJAX sub-flows (jQuery → Controller → JSON):
- `cart.js` `addToCart()` → `POST /cart/{id}` → `CartController` → `CartService` (session)
- `ordersPage.js` row expand → `GET /orders/{id}` (Accept: application/json) → `OrderController.getProductInfos()` → `OrderService`
- `productsPage.js` filter/sort → `GET /products` with query params → `ProductController` → `ProductRepositoryCriteriaImpl`

### 3) Layer/Module Responsibilities

| Layer or module | Owns | Must not own | Evidence |
|-----------------|------|--------------|----------|
| `controller/` | HTTP routing, model population, session reads, redirect construction via `Constants.REDIRECT + Views.*` | Direct DB calls, business rules | `ProductController.java`, `OrderController.java` |
| `service/` | `@Transactional` boundaries, entity assembly, orchestration | HTTP servlet API, view names | `OrderService.java`, `CartService.java` |
| `repository/` Spring Data interfaces | CRUD, paging, sorting via derived query methods | Any logic beyond querying | `OrderRepository.java`, `UserRepository.java` |
| `repository/impl/` | Complex JPA Criteria queries (`ProductRepositoryCriteriaImpl`) and file I/O (`AvatarRepositoryImpl`) | Business logic | `ProductRepositoryCriteriaImpl.java` |
| `domain/` | JPA entities (`Product`, `Order`, `User`, …) + static metamodels (`Product_`) | Request/response shapes | `domain/` listing |
| `entity/` | Request beans (`ProductBean`, `OrdersBean`), session objects (`ShoppingCart`), response DTOs (`OrderDto`) | Persistence annotations | `entity/` listing |
| `security/` | Login success/failure handling; attempt tracking/ban in `FailureHandler`; reset in `SuccessHandler` | General app logic | `FailureHandler.java`, `SuccessHandler.java` |
| `captcha/` | Captcha token generation and validation (strategy pattern) | Auth or business rules | `CaptchaSettings.java`, `strategy/` |
| `config/` | Spring beans: `SecurityFilterChain`, `LocalizationConfig` interceptor, `UserConfig` (UserDetailsService) | Feature logic | `SecurityConfig.java`, `LocalizationConfig.java` |
| `util/` | String constants and `@ConfigurationProperties` beans | Any logic | `Views.java`, `*Props.java` |

### 4) Reused Patterns

| Pattern | Where found | Why it exists |
|---------|-------------|---------------|
| Strategy | `captcha/strategy/` (`CaptchaProviderStrategy` interface + `HiddenField`/`Cookie`/`Session` impls) | Swap captcha delivery mechanism via single `@Bean` in `SecurityConfig` |
| Repository (Spring Data) | `repository/*.java` | Declarative CRUD/paging without boilerplate |
| Template Method (Criteria) | `ProductRepositoryCriteriaImpl` | Reusable dynamic filter+sort+page query construction |
| Session-backed state | `entity/ShoppingCart.java` stored under `Attributes.CART` | Stateful cart without DB persistence |
| `@ModelAttribute` | All controllers | Inject common model data (page props, sort options, cart count) before handler methods |
| Constants class | `util/Views`, `util/Attributes`, `util/Parameters`, `util/Constants` | Eliminate string-literal duplication |

### 5) Known Architectural Risks

- **Session-scoped cart** — `ShoppingCart` is stored in `HttpSession`. Multiple app instances require sticky sessions or distributed session storage; no such infrastructure is present.
- **Hardcoded credentials in `application.yml`** — `root`/`admin` committed in plain text; no secrets management layer.
- **CSRF disabled** (`csrf().disable()` in `SecurityConfig`) — removes Spring's built-in CSRF token protection; all state-changing endpoints are vulnerable to CSRF attacks from other origins.
- **Unimplemented order cancellation** — `// TODO: 007` in `OrderController.java:84`; partially modelled feature.

### 6) Evidence

- `src/main/java/com/springtraining/furnitureshop/FurnitureShopApplication.java`
- `src/main/java/com/springtraining/furnitureshop/controller/`
- `src/main/java/com/springtraining/furnitureshop/service/OrderService.java`
- `src/main/java/com/springtraining/furnitureshop/repository/impl/ProductRepositoryCriteriaImpl.java`
- `src/main/java/com/springtraining/furnitureshop/config/SecurityConfig.java`
- `src/main/java/com/springtraining/furnitureshop/entity/ShoppingCart.java`

