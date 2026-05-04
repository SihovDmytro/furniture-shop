# AGENTS.md

## Project Snapshot
- Stack: Spring Boot 2.7 (Java 11), Thymeleaf + jQuery frontend, Spring Security, Spring Data JPA, MySQL (`pom.xml`).
- Entry point is `src/main/java/com/springtraining/furnitureshop/FurnitureShopApplication.java`.
- Runtime config is in `src/main/resources/application.yml`; tests use `src/test/resources/application.yml`.
- Data seed/schema lives in `Dump20240523.sql` (DB `furniture_shop`).

## Architecture And Data Flow
- Controllers in `controller/` render Thymeleaf pages and expose small JSON endpoints used by page JS.
- Services in `service/` are thin transaction boundaries; most query complexity sits in repositories.
- Repositories in `repository/` mix Spring Data interfaces with custom implementations (`repository/impl/`).
- Request flow example: `products.html` -> `productsPage.js` `addToCart()` -> `CartController` -> `CartService` session cart.
- Order flow: `cart.html` POST `/orders` -> `OrderController.createOrder()` -> `OrderService.createOrder()` persists `Order` + `OrderProductInfo`.
- Product catalog flow: `ProductController.getProductsPage()` -> `ProductRepositoryCriteriaImpl` builds JPA Criteria filters/sort/pagination.

## Security, Session, And Captcha
- `SecurityConfig` protects `/homePage` and `/orders` for `ROLE_USER`; most other routes are public.
- Login behavior is in custom handlers: `security/FailureHandler.java` (attempt tracking/ban) and `security/SuccessHandler.java` (reset attempts).
- `User` entity implements `UserDetails` directly; authentication principal is cast to `com.springtraining.furnitureshop.domain.User`.
- Cart state is session-backed (`Attributes.CART`), implemented by `entity/ShoppingCart.java`.
- Registration uses captcha with one-time token map in servlet context; active strategy bean is hidden-field (`CaptchaProviderHiddenFieldStrategyImpl` from `SecurityConfig`).
- Registration page expiration is enforced via `CaptchaSettings.MAX_INTERVAL` and hidden `pageGenerationTime` in `registration.html`.

## Project-Specific Conventions
- Reuse string constants from `util/Views.java`, `util/Attributes.java`, and `util/Parameters.java` instead of inline literals.
- Config defaults are injected via `@ConfigurationProperties` classes in `util/*Props.java` (products/orders/pagination/user/avatar/localization).
- Controllers commonly keep validation errors in session across redirects (`RegistrationController`, `LoginController`).
- Logging pattern is mostly `log.trace("... start")` + `log.info(Constants.LOGGER_FORMAT, key, value)`; keep new logs consistent.
- Sort/filter request models are DTO-like beans in `entity/` (`ProductBean`, `OrdersBean`), not entities.
- `Constants.REDIRECT` is `"redirect:/"`; current code builds redirects as `Constants.REDIRECT + Views.X`.

## Frontend And Integration Contracts
- AJAX contracts are hard-coded in JS: `static/js/cart.js`, `productsPage.js`, `ordersPage.js`; preserve payload shapes.
- Cart endpoints expect raw JSON number for add/remove and object `{ productID, quantity }` for update.
- Orders details endpoint is `GET /orders/{id}` with `application/json` and is consumed by dynamic table expansion in `ordersPage.js`.
- Localization depends on `lang` query parameter (`static/js/localization.js`) and `LocalizationConfig` interceptor.
- Avatar uploads are persisted by `AvatarRepositoryImpl` under `src/main/resources/static/avatars/` using configured `furnitureshop.avatar.directory`.

## Developer Workflows
- Build and test with Maven (no wrapper in repo): `mvn clean test`, `mvn spring-boot:run`.
- App startup expects MySQL reachable at `jdbc:mysql://localhost:3306/furniture_shop` (`src/main/resources/application.yml`).
- Tests are integration-heavy (`@SpringBootTest`) and test config points to `furniture_shop_test`; provision DB before running full suite.
- To seed local data quickly, import `Dump20240523.sql` before running UI flows that expect catalog/orders/user records.

## Change Checklist For Agents
- If adding/changing endpoints, update both controller mappings and corresponding template/JS callers.
- If adding new request params/model attrs, align names with `Parameters`/`Attributes` constants and Thymeleaf bindings.
- If changing product/order sorting/filtering, update bean defaults (`*Props`), controller fallback logic, and repository query code together.
- If touching auth logic, validate behavior in both handlers and `User.isAccountNonLocked()`.
- If changing localization tags, update `LocalizationTags` and message bundles (`messages*.properties`) in the same change.

