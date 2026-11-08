# AGENTS.md — hive-domain

- This module holds shared domain types used by the Hive application: JPA entities, repositories, enums/models, and Spring domain configuration.
- Keep persistence mappings and repository contracts aligned with `hive-oss` migrations. Schema changes belong in the consuming application's Flyway migrations, not ad hoc entity-driven schema creation; the application validates mappings at startup.
- Avoid adding application/API orchestration here. Keep the module reusable and focused on domain/persistence types.
- Run module checks from this directory (`mvn test`); if building from a clean local Maven repository, install the parent `external-bom` first.
