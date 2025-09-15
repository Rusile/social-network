# Social Network API

Учебный проект: простой backend социальной сети.

---

## Стек
- **Kotlin + Spring Boot**
- **PostgreSQL**
- **jOOQ** — типобезопасный доступ к БД.
- **Spring Security + JWT** — авторизация
- **Gradle (KTS)** — сборка
- **Docker Compose** — окружение (БД + приложение)

---

## БД
- База поднимается через Postgres в Docker.
- `db/init.sql` применяется при первом старте.
- В качестве QueryBuilder используется JOOQ. Также с помощью данной библиотеки настроена кодогенерация сущностей из БД. Автогенерация запросов не используется, все запросы написаны вручную.

---

## OpenApi
- Файл спецификации представлен [тут](src/main/resources/api/openapi.json)

---

## JWT
- Авторизация полностью **stateless**: токены не хранятся, только валидируются и парсятся.

## Запуск приложения

```bash
  docker compose -f docker/docker-compose.yml up -d --build      
```


