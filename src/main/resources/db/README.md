# Database Migrations

The folder `db` contains SQL files, that will be migrated to the database when quarkus is starting.

## What is a migration?

A database migration is a version-controlled change to the database schema, allowing developers to manage and apply changes consistently across different environments (e.g., development, testing, production). Migrations typically include creating or altering tables, columns, indexes, or other database objects and are usually written as scripts or in code using a migration tool (like Flyway or Liquibase).

Once a migration is added and applied, it **must not be changed** because it represents a historical change that has already been applied to other environments or databases. Altering a migration retroactively can lead to inconsistencies, errors, or data loss, as other systems or team members may rely on the migration's original state. Instead of changing an existing migration, **new migrations** should be created to address any further changes needed.

### In Short:

-   **Never change existing migration files.**
-   **Always add new migration files to change the database.**
-   **Never change a database schema with a database management tool (like DBeaver)**

## Versioning Scheme

In order for base migrations and environment specific migrations to be compatible, we need a versioning scheme that is always increasing and never conflicts with existing migrations.

Therefore we use the date of the migration (**yyyyMMdd**) followed by the environment specifier **0** or **1** and then an increasing version, in case we push multiple changes on the same day.

The environment specifier indicates whether it is a base migration **(0)** or an environment specific migration **(1)**.

(**ATTENTION): Double underscore "\_\_" is needed between version and title**)

An example for a base migration on 16.08.2024 would be: `V20240816.0.1__Initial_DB_Schema.sql`

# Base migrations

The folder `db/migration` contains the base migrations. These will be migrated to **EVERY** Environment first.

-   Use `V<DATE>.0.` as prefix e.g. `V20240816.0.1__Initial_DB_Schema.sql`
-   The `.0.` identifies it as a Base Migration

# ENV migrations

The folders `db/migration-ENV` contain the environment specific migrations. These will only be migrated to the mentioned environments.

-   Use `V<DATE>.1.` as prefix e.g. `V20240816.1.0__INT_Changes.sql`
-   The `.1.` identifies it as a Env specific Migration

# Local Test Data migrations

The folder `db/migration-local` contains the migrations for local debugging and running integration tests. These will only be migrated to your local test database during testing and developing (quarkusDev).

-   Use `V<DATE>.21.` as prefix e.g. `V20240816.1.0__Add_Test_Data.sql`
-   The `.1.` identifies it as a Local Test Migration
