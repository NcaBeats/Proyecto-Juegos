# Proyecto MicroServicios - Videojuegos

## Descripción del Proyecto

Sistema de gestión de videojuegos basado en una arquitectura de microservicios. Permite administrar juegos, usuarios, perfiles, wishlists, reseñas, compras, notificaciones, biblioteca de juegos, solicitudes de amistad y estadísticas.

## Arquitectura

Tienes 10 microservicios, cada uno corriendo en su propio puerto:  

- ms-juego (8080): Maneja el catálogo de juegos, estudios, géneros y plataformas. Es el núcleo donde están todos los juegos disponibles.  
---
- ms-usuario (8081): Gestiona los usuarios y su saldo.  
---
- ms-profile (8082): Perfiles de usuario, nickname, avatar, bio.  
---
- ms-wishlist (8085): Lista de deseos de cada usuario.  
---
- ms-review (8084): Sistema de reseñas y ratings.  
---
- ms-purchase (8083): Procesa las compras.  
---
- ms-notification (8086): Envía notificaciones al usuario cuando ocurre algo (compra, reseña, wishlist).  
---
- **ms-library (8088): La biblioteca personal de cada usuario, los juegos que ha comprado.  
---
- ms-friendship (8089): Sistema de amigos y solicitudes de amistad.  
---
- ms-stats (8087): Un servicio especial sin base de datos que consulta a los demás para dar estadísticas globales.    
---

###   Cómo se comunican?

  Usan Feign Client para llamar a otros microservicios.  
  Por ejemplo, cuando compras un juego, el servicio de purchase le avisa a:
- ms-library para agregar los juegos a tu biblioteca  


- ms-notification para decirte que compraste algo


- ms-usuario para restarte el saldo


### La base de datos
  Cada microservicio tiene su propia base de datos PostgreSQL en Docker.  

  Son 9 contenedores (ms-stats no tiene DB porque solo consulta).  
  
Cada uno con su propio puerto
## Pre-requisitos

- **Java**: JDK 25 (Temurin)  
  ![Java](https://img.shields.io/badge/Java-25-orange?logo=java&logoColor=white)
- **Docker**: Docker Desktop  
  ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker&logoColor=white)
- **IDE**: IntelliJ IDEA (recomendado) o VS Code  
  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-black?logo=intellij-idea&logoColor=white) ![VS Code](https://img.shields.io/badge/VS_Code-blue?logo=visualstudiocode&logoColor=white)

## Tecnologías Utilizadas

- **Framework**: Spring Boot 4.0.5  
  ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.5-green?logo=springboot)
- **Base de datos**: PostgreSQL 17 (10 contenedores)  
  ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql)
- **Migraciones**: Flyway  
  ![Flyway](https://img.shields.io/badge/Flyway-Migrations-red)
- **Comunicación**: REST + Feign Client  
  ![REST API](https://img.shields.io/badge/REST-API-orange?logo=fastapi)
  ![Feign Client](https://img.shields.io/badge/Feign_Client-HTTP%20Client-purple)
- **Mapeo**: MapStruct    
  ![MapStruct](https://img.shields.io/badge/MapStruct-Mapper-orange)
- **Build**: Maven  
  ![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)

## Estructura de la Base de Datos

### PostgreSQL Containers

| Microservicio   | Puerto | Base de Datos   |
|-----------------|--------|-----------------|
| ms-juego        | 5432   | db_juego        |
| ms-usuario      | 5433   | db_usuario      |
| ms-profile      | 5434   | db_profile      |
| ms-wishlist     | 5435   | db_wishlist     |
| ms-review       | 5436   | db_review       |
| ms-purchase     | 5437   | db_purchase     |
| ms-notification | 5438   | db_notification |
| ms-library      | 5439   | db_library      |
| ms-friendship   | 5440   | db_friendship   |
| ms-stats        | -      | (sin DB)        |

## Cómo Iniciar el Proyecto

### Paso 1: Levantar contenedores PostgreSQL

```bash
docker-compose up -d
```

### Paso 2: Compilar el proyecto

En IntelliJ IDEA:
1. Build → Build Project

O desde terminal:
```bash
mvn clean install -DskipTests
```

### Paso 3: Ejecutar los microservicios

Ejecutar cada microservicio desde IntelliJ (Run/Debug):

1. MsJuegoApplication (8080)
2. MsUsuarioApplication (8081)
3. MsProfileApplication (8082)
4. MsLibraryApplication (8088)
5. MsNotificationApplication (8086)
6. MsPurchaseApplication (8083)
7. MsReviewApplication (8084)
8. MsWishlistApplication (8085)
9. MsStatsApplication (8087)
10. MsFriendshipApplication (8089)

### Verificar que todo funciona

```bash
docker ps
```

Deberías ver 9 contenedores PostgreSQL corriendo.

## Seed Data

Al iniciar, las siguientes tablas tienen datos de ejemplo:

### ms-juego (db_juego)
- **estudio**: CD Projekt Red, FromSoftware, Santa Monica Studio, Rockstar Games
- **genero**: RPG, Acción, Aventura, Mundo Abierto, Souls-like
- **plataforma**: PC, PlayStation 5, Xbox Series X, Nintendo Switch
- **juego**: The Witcher 3, Cyberpunk 2077, Elden Ring, God of War, Red Dead Redemption 2

### ms-usuario (db_usuario)
- **usuario**: Juan Perez, Maria Gonzalez, Carlos Rojas, Fernanda Soto, Diego Muñoz (todos con saldo $100)

## Endpoints

---

## ms-juego (Puerto: 8080)

### Juegos

| Método | Endpoint                                   | Descripción                        |
|--------|--------------------------------------------|------------------------------------|
| GET    | `/api/v1/juegos`                           | Listar todos los juegos (paginado) |
| GET    | `/api/v1/juegos/{id}`                      | Obtener juego por ID               |
| GET    | `/api/v1/juegos/estudio/{estudioId}`       | Juegos por estudio                 |
| GET    | `/api/v1/juegos/genero/{generoId}`         | Juegos por género                  |
| GET    | `/api/v1/juegos/plataforma/{plataformaId}` | Juegos por plataforma              |
| GET    | `/api/v1/juegos/buscar?nombre=X`           | Buscar juego por nombre            |
| POST   | `/api/v1/juegos`                           | Crear nuevo juego                  |
| PUT    | `/api/v1/juegos/{id}`                      | Actualizar juego                   |
| DELETE | `/api/v1/juegos/{id}`                      | Eliminar juego                     |

### Géneros

| Método | Endpoint               | Descripción    |
|--------|------------------------|----------------|
| GET    | `/api/v1/generos`      | Listar todos   |
| GET    | `/api/v1/generos/{id}` | Obtener por ID |
| POST   | `/api/v1/generos`      | Crear          |
| PUT    | `/api/v1/generos/{id}` | Actualizar     |
| DELETE | `/api/v1/generos/{id}` | Eliminar       |

### Plataformas

| Método | Endpoint                   | Descripción    |
|--------|----------------------------|----------------|
| GET    | `/api/v1/plataformas`      | Listar todas   |
| GET    | `/api/v1/plataformas/{id}` | Obtener por ID |
| POST   | `/api/v1/plataformas`      | Crear          |
| PUT    | `/api/v1/plataformas/{id}` | Actualizar     |
| DELETE | `/api/v1/plataformas/{id}` | Eliminar       |

### Estudios

| Método | Endpoint                | Descripción    |
|--------|-------------------------|----------------|
| GET    | `/api/v1/estudios`      | Listar todos   |
| GET    | `/api/v1/estudios/{id}` | Obtener por ID |
| POST   | `/api/v1/estudios`      | Crear          |
| PUT    | `/api/v1/estudios/{id}` | Actualizar     |
| DELETE | `/api/v1/estudios/{id}` | Eliminar       |

---

## ms-usuario (Puerto: 8081)

| Método | Endpoint                                  | Descripción             |
|--------|-------------------------------------------|-------------------------|
| GET    | `/api/v1/usuarios`                        | Listar todos (paginado) |
| GET    | `/api/v1/usuarios/{id}`                   | Obtener por ID          |
| GET    | `/api/v1/usuarios/buscar/email/{email}`   | Buscar por email        |
| GET    | `/api/v1/usuarios/buscar/nombre/{nombre}` | Buscar por nombre       |
| POST   | `/api/v1/usuarios`                        | Crear usuario           |
| PUT    | `/api/v1/usuarios/{id}`                   | Actualizar usuario      |
| DELETE | `/api/v1/usuarios/{id}`                   | Eliminar usuario        |
| PUT    | `/api/v1/usuarios/{id}/balance?monto=X`   | Restar saldo            |

---

## ms-profile (Puerto: 8082)

| Método | Endpoint                               | Descripción             |
|--------|----------------------------------------|-------------------------|
| GET    | `/api/v1/profiles`                     | Listar todos (paginado) |
| GET    | `/api/v1/profiles/{userId}`            | Obtener por userId      |
| GET    | `/api/v1/profiles/nickname/{nickname}` | Buscar por nickname     |
| POST   | `/api/v1/profiles`                     | Crear perfil            |
| PUT    | `/api/v1/profiles`                     | Actualizar perfil       |
| DELETE | `/api/v1/profiles/{userId}`            | Eliminar perfil         |

---

## ms-wishlist (Puerto: 8085)

| Método | Endpoint                              | Descripción              |
|--------|---------------------------------------|--------------------------|
| GET    | `/api/v1/wishlists/{userId}`          | Ver wishlist de usuario  |
| POST   | `/api/v1/wishlists/{userId}`          | Agregar juego a wishlist |
| DELETE | `/api/v1/wishlists/{userId}/{gameId}` | Quitar juego de wishlist |

---

## ms-review (Puerto: 8084)

| Método | Endpoint                        | Descripción           |
|--------|---------------------------------|-----------------------|
| GET    | `/api/v1/reviews/game/{gameId}` | Reseñas de un juego   |
| GET    | `/api/v1/reviews/user/{userId}` | Reseñas de un usuario |
| POST   | `/api/v1/reviews`               | Crear reseña          |
| PUT    | `/api/v1/reviews/{id}`          | Actualizar reseña     |
| DELETE | `/api/v1/reviews/{id}`          | Eliminar reseña       |

---

## ms-purchase (Puerto: 8083)

| Método | Endpoint                               | Descripción                  |
|--------|----------------------------------------|------------------------------|
| GET    | `api/v1/purchases`                     | Listar todas las compras     |
| GET    | `api/v1/purchases/{id}`                | Compras de un usuario        |
| POST   | `api/v1/purchases`                     | Crear compra                 |
| GET    | `api/v1/purchases/game/{gameId}/stats` | Stats de compras por juego   |
| GET    | `api/v1/purchases/user/{userId}/stats` | Stats de compras por usuario |

---

## ms-notification (Puerto: 8086)

| Método | Endpoint                              | Descripción                                 |
|--------|---------------------------------------|---------------------------------------------|
| GET    | `/api/v1/notifications/user/{userId}` | Notificaciones de usuario                   |
| POST   | `/api/v1/notifications`               | Crear notificación                          |
| POST   | `/api/v1/notifications/purchase       | Crear notificación específica para purchase |

---

## ms-library (Puerto: 8088)

| Método | Endpoint                                        | Descripción                     |
|--------|-------------------------------------------------|---------------------------------|
| GET    | `/api/v1/library/{userId}`                      | Ver biblioteca de usuario       |
| POST   | `/api/v1/library/{userId}/games`                | Agregar juego a biblioteca      |
| DELETE | `/api/v1/library/{userId}/game/{gameId}`        | Eliminar juego de la biblioteca |
| GET    | `/api/v1/library/{userId}/game/{gameId}/exists` | Verificar si juego existe       |

---

## ms-friendship (Puerto: 8089)

| Método | Endpoint                               | Descripción                 |
|--------|----------------------------------------|-----------------------------|
| GET    | `/api/v1/friendships/{userId}/friends` | Ver amigos de usuario       |
| GET    | `/api/v1/friendships/{userId}/pending` | Solicitudes pendientes      |
| POST   | `/api/v1/friendships/request`          | Enviar solicitud de amistad |
| PUT    | `/api/v1/friendships/{id}/accept`      | Aceptar solicitud           |
| PUT    | `/api/v1/friendships/{id}/reject`      | Rechazar solicitud          |
| DELETE | `/api/v1/friendships/{id}`             | Eliminar amistad            |

---

## ms-stats (Puerto: 8087)

Este microservicio usa Feign para consultar datos de otros servicios y no tiene base de datos propia.

| Método | Endpoint                      | Descripción             |
|--------|-------------------------------|-------------------------|
| GET    | `/api/v1/stats/game/{gameId}` | Estadísticas de juego   |
| GET    | `/api/v1/stats/user/{userId}` | Estadísticas de usuario |

---

## JSON de Ejemplo

### POST - Crear Juego (ms-juego)

```json
{
  "nombre": "Hollow Knight",
  "descripcion": "Metroidvania atmosférico en un mundo de insectos",
  "precio": 14.99,
  "fechaLanzamiento": "2017-02-24",
  "estado": "ACTIVO",
  "estudioId": 2,
  "generoIds": [1, 2, 3],
  "plataformaIds": [1, 2, 3, 4]
}
```

### POST - Crear Usuario (ms-usuario)

```json
{
  "nombre": "Pedro García",
  "email": "pedro.garcia@mail.com",
  "saldo": 150.00
}
```

### POST - Crear Profile (ms-profile)

```json
{
  "userId": 1,
  "nickname": "gamer123",
  "avatar": "https://ejemplo.com/avatar.png",
  "bio": "Jugador de RPGs y juegos indie",
  "tipoPerfil": "PRIVADO"
}
```

### POST - Agregar a Wishlist (ms-wishlist)

```json
{
  "gameId": 3
}
```

### POST - Crear Review (ms-review)

```json
{
  "userId": 1,
  "juegoId": 1,
  "comentario": "Excelente juego, historia increíble",
  "rating": "CINCO_ESTRELLAS"
}
```

### POST - Crear Compra (ms-purchase)

```json
{
  "userId": 1,
  "juegos": [
    {
      "gameId": 2
    },
    {
      "gameId": 4
    }
  ]
}
```

### POST - Crear Notificación (ms-notification) este endpoint se llama desde ms-purchase, ms-review y ms wishlist para notificar al usuario sobre eventos importantes

```json
{
  "userId": 1,
  "message": "Has añadido Cyberpunk 2077 a tu biblioteca",
  "tipo": "COMPRA",
  "gameId": 2
}
```

### POST - Agregar a Biblioteca (ms-library), este endpoint se llama desde ms-purchase al crear una compra exitosa

```json
{
  "gameIds": [2, 4]
}
```

### POST - Crear Amistad (ms-friendship)

```json
{
  "friendId": 2
}
```

---

## Estados de Juegos

- **ACTIVO**: Juego disponible
- **NO_DISPONIBLE**: Juego no disponible
- **PROXIMAMENTE**: Juego eliminado del catálogo

## Ratings de Reseñas

- **CINCO_ESTRELLAS**: 5 estrellas
- **CUATRO_ESTRELLAS**: 4 estrellas
- **TRES_ESTRELLAS**: 3 estrellas
- **DOS_ESTRELLAS**: 2 estrellas
- **UNA_ESTRELLA**: 1 estrella

## Tipos de Perfil

- **PUBLICO**: Perfil público
- **PRIVADO**: Perfil privado

## Tipos de Notificación

- **COMPRA**: Notificación de compra realizada
- **REVIEW**: Notificación de reseñas
- **LISTA_DE_DESEOS**: Notificaciones de la wishlist

---

## Cómo Hacer Reset de la Base de Datos

Para restaurar los datos originales del seed:

```bash
docker-compose down -v
docker-compose up -d
```

Esto eliminará todos los datos y ejecutará las migraciones de Flyway desde cero.

---

## Problemas Comunes

### Error de conexión a PostgreSQL
- Verificar que Docker esté corriendo: `docker ps`
- Verificar que los contenedores estén levantados: `docker-compose ps`

### Error de Flyway
- Eliminar volúmenes: `docker-compose down -v`
- Volver a levantar: `docker-compose up -d`

---

## Autores

👨‍💻 Nicolás Carvajal  
![GitHub](https://img.shields.io/badge/GitHub-NcaBeats-black?logo=github)  
🔗 https://github.com/NcaBeats

---

👨‍💻 Benjamín Soto  
![GitHub](https://img.shields.io/badge/GitHub-benjaminsotoarrano-black?logo=github)  
🔗 https://github.com/benjaminsotoarrano  