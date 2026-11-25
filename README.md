# Backend Tickets Colegio Marcela Paz

Microservicios en Spring Boot para la app móvil **MPAZ Ticket** (reporte de problemas de mantención en la Escuela Escritora Marcela Paz).

## Microservicios

| Servicio | Puerto | Base de datos (Neon) | Responsabilidad |
|---|---|---|---|
| `usuarios-service` | 8081 | `usuarios_db` | Registro e inicio de sesión (contraseñas con BCrypt) |
| `tickets-service` | 8082 | `tickets_db` | CRUD de tickets de mantención |

Cada microservicio es un proyecto Spring Boot independiente, con su propia base de datos y su propio puerto.

## Tecnologías

Java 21 · Spring Boot 4.2 · Spring Web MVC · Spring Data JPA · Bean Validation · Lombok · PostgreSQL (Neon) · BCrypt (spring-security-crypto)

## Endpoints

### usuarios-service (`http://localhost:8081`)

| Método | Ruta | Body | Respuesta |
|---|---|---|---|
| POST | `/api/usuarios/registro` | `{ "nombre", "email", "password" }` | 201 usuario creado · 400 datos inválidos · 409 correo ya registrado |
| POST | `/api/usuarios/login` | `{ "email", "password" }` | 200 usuario · 401 credenciales incorrectas |
| GET | `/api/usuarios/{id}` | — | 200 usuario · 404 no existe |

La respuesta nunca incluye la contraseña: `{ "id", "nombre", "email" }`.

### tickets-service (`http://localhost:8082`)

| Método | Ruta | Body | Respuesta |
|---|---|---|---|
| GET | `/api/tickets?usuarioId={id}` | — | 200 lista de tickets del usuario |
| GET | `/api/tickets/{id}` | — | 200 ticket · 404 no existe |
| POST | `/api/tickets` | `{ "usuarioId", "titulo", "descripcion", "sector", "urgencia" }` | 201 ticket creado · 400 datos inválidos |
| PUT | `/api/tickets/{id}` | igual que POST | 200 ticket actualizado |
| PATCH | `/api/tickets/{id}/estado` | — | 200 ticket con el siguiente estado (Abierto → En Proceso → Cerrado) |
| DELETE | `/api/tickets/{id}` | — | 204 eliminado · 404 no existe |

## Cómo ejecutar

1. Crear en Neon las bases `usuarios_db` y `tickets_db`.
2. En cada servicio, copiar `.env.example` como `.env` y completar los datos de conexión (el `.env` no se sube al repositorio).
3. Levantar cada servicio en su propia terminal:

```powershell
cd usuarios-service
.\mvnw.cmd spring-boot:run
```

```powershell
cd tickets-service
.\mvnw.cmd spring-boot:run
```

Desde el emulador de Android, la app accede a los servicios en `http://10.0.2.2:8081` y `http://10.0.2.2:8082`.
