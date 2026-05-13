# OpenCode — Contexto rápido del proyecto

Propósito
- Documento para el "OpenCode del futuro" (o cualquier desarrollador/agent) que retome trabajo en este repo con el mínimo contexto y gasto de tokens.
- Contiene: arquitectura, servicios, puertos, puntos importantes, decisiones de diseño relevantes, migraciones, tareas pendientes y comandos útiles.

Resumen de arquitectura
- Monorepo con microservicios Spring Boot (Maven). Cada módulo es un microservicio independiente.
- Persistencia: JPA + Hibernate. En dev usan H2 (in-memory). Algunos módulos incluyen scripts Flyway.
- Comunicación entre servicios: Feign Clients.
- Auditoría JPA habilitada con @EnableJpaAuditing + @CreatedDate en entidades.

Servicios y puertos (dev — ver `application.yaml` por microservicio)
- ms-juego: 8080
- ms-usuario: 8081
- ms-profile: 8082
- ms-review: 8084
- ms-wishlist: 8085
- ms-notification: 8086

Rutas REST principales (resumen)
- ms-juego: `/api/v1/juegos`
  - GET /{id}, GET (paged), GET /buscar?nombre=, POST, PUT /{id}, DELETE /{id}
- ms-usuario: `/api/v1/usuarios`
  - GET, GET /{id}, GET /buscar, POST, PUT /{id}, DELETE /{id}
- ms-profile: `api/v1/profiles`
  - GET, GET /{id}, GET /buscar, POST, PUT, DELETE /{userId}
- ms-review: `/api/v1/reviews`
  - GET /{id} (findAllByJuegoId paginado), POST, PUT /{id}, DELETE /{id}
- ms-wishlist: `/api/v1/wishlist-games`
  - GET /{userId}, POST /{userId}, DELETE /{userId}/{gameId}
- ms-notification: `/api/v1/notifications`
  - GET /{userId}, POST

Patrón y estructura por servicio
- Controller → Service → Repository
- DTOs (Java records) con validaciones `jakarta.validation` (@NotBlank, @NotNull, @Size, ...)
- Mappers (@Component) para transformar Entidad ↔ DTO
- Repositorios extienden `JpaRepository` y contienen queries cuando hace falta

Entidades críticas (ubicaciones relativas)
- Juego: `ms-juego/src/main/java/com/app/msjuego/model/Juego.java`
- Usuario: `ms-usuario/src/main/java/com/app/msusuario/model/Usuario.java`
- Profile: `ms-profile/src/main/java/com/app/msprofile/model/Profile.java`
- Review: `ms-review/src/main/java/com/example/msreview/model/Review.java`
- Wishlist y WishlistGame: `ms-wishlist/src/main/java/com/app/mswishlist/wishlist` y `.../wishlistgame`
- Notification: `ms-notification/src/main/java/com/app/msnotification/model/Notification.java`

Decisiones de diseño (importante)
- Relacionar con JPA solo las entidades que comparten la misma base de datos (p. ej. Wishlist ↔ WishlistGame).
- Referencias a recursos gestionados por otros microservicios se modelan como IDs primitivos (Long userId, Long juegoId). Esto es intencional:
  - Reduce acoplamiento y evita integridad referencial cross-DB.
  - Requiere validación explícita de existencia del recurso remoto antes de persistir.

Migraciones / Flyway
- Algunos módulos tienen `src/main/resources/db/migration/V*_*.sql` (ej.: ms-juego, ms-usuario). ms-profile tiene un V1 vacío — revisar si debe completarse o eliminarse.
- `application.yaml` en varios módulos tiene flyway configurado o comentado. Verificar antes de activar en todos los servicios.

Validaciones y observaciones relevantes
- DTOs usan `jakarta.validation` y controllers usan `@Valid` en los cuerpos de request.
- Observación detectada: en `ms-juego/src/main/java/.../JuegoRequest.java` se usó `@NotBlank` sobre un campo `EstadoJuego` (enum) — debe ser `@NotNull` (corregir).
- Evitar `@Valid` en `@PathVariable` (no tiene efecto).

Comunicación entre microservicios
- Feign Clients usados (p. ej. `JuegoClient`, `ProfileClient`) y `@EnableFeignClients` habilitado.
- No hay configuración global visible de timeouts/retries; es recomendable configurar timeouts/decoders y manejar excepciones de Feign en la capa de servicio.

Tareas pendientes (prioritarias) — usa los números para referencia rápida
1. Corregir validación enum: cambiar `@NotBlank EstadoJuego estado` → `@NotNull EstadoJuego estado` en `ms-juego`.
2. Añadir dialecto de Hibernate en `application.yaml` de cada servicio (dev: H2Dialect). Ej:
   `spring.jpa.properties.hibernate.dialect: org.hibernate.dialect.H2Dialect`
3. Manejo básico de errores en llamadas Feign: mapear 404 → `EntityNotFoundException` y capturar errores remotos.
4. Añadir `@ControllerAdvice` global para mapear excepciones a respuestas JSON (EntityNotFound → 404, MethodArgumentNotValid → 400, Feign/timeouts → 503).
5. Revisar y completar/limpiar scripts Flyway (archivos vacíos).
6. (Opcional) Añadir resiliencia (Resilience4j o retries en Feign) y timeouts.

Cómo rehidratar la conversación con el mínimo de tokens
- Al volver a abrir la sesión, pega únicamente:
  - Commit hash corto: `git rev-parse --short HEAD` (ej: `abcdef1`)
  - Branch actual
  - Número(s) de tarea(s) que quieres que el asistente ejecute (usar la lista de Tareas pendientes)
  - Cambios de configuración que prefieres (ej: dialecto H2 o Postgres)
  - Si quieres que produzca cambios directos, autoriza explícitamente (ej: "autoriza aplicar tareas 1 y 2").

Comandos útiles (rápidos)
- Ejecutar servicio: desde la carpeta del servicio `mvn spring-boot:run`
- Commit actual: `git rev-parse --short HEAD`
- Crear este README manualmente (si prefieres copiar/pegar):
  - Unix:
    ```bash
    mkdir -p docs
    cat > docs/README-OPEN-CODE.md <<'EOF'
    (pega aquí el contenido)
    EOF
    ```
  - PowerShell:
    ```powershell
    New-Item -ItemType Directory -Force -Path docs
    Set-Content -Path docs/README-OPEN-CODE.md -Value @'
    (pega aquí el contenido)
    '@
    ```

Notas finales
- El README está pensado para ahorrar tokens: con el commit hash + tarea(s) y autorización explícita, el asistente podrá actuar con contexto suficiente.
- Si quieres, puedo aplicar ahora cambios mínimos: (1) corregir la validación enum y (2) añadir la propiedad de dialecto H2 en `application.yaml` de cada servicio. Autoriza explícitamente si quieres que los aplique.

---
Archivo creado por OpenCode — mantener en la raíz del repo como `README-OPEN-CODE.md`.
