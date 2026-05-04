---
applyTo: "src/main/java/com/springtraining/furnitureshop/service/**/*.java"
---

# Service Layer – Javadoc Requirement

Every **public method** in a service class must have a Javadoc comment.

## Rules

- All public methods — including constructors — must be preceded by a `/** ... */` Javadoc block.
- The Javadoc must include:
  - A summary sentence describing **what** the method does (first line).
  - `@param` tag for every parameter, with a short description.
  - `@return` tag if the return type is not `void`, describing what is returned.
  - `@throws` tag for every checked exception declared in the signature, and for any significant unchecked exception that a caller should be aware of.
- Do **not** add Javadoc to `private`, `protected`, or package-private methods (those are optional, not required by this rule).
- Do **not** simply restate the method name — the description must add meaningful context about business logic or side effects (e.g. session/cart mutation, persistence, email sending).

