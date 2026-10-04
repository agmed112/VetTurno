# VetTurno

API REST para organizar la agenda de **Veterinaria Huellitas**. Permite registrar propietarios, mascotas, veterinarios y citas, evitando cruces de horario para un mismo veterinario.

## Tecnologías

- Java 17
- Spring Boot 3.5.13
- Maven
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Spring Security
- JWT (JJWT 0.12.7)
- Bean Validation
- Swagger / OpenAPI (springdoc 2.8.17)

## Arquitectura

El proyecto separa responsabilidades en `controller`, `service`, `repository`, `model`, `dto`, `security`, `config` y `exception`.

## Configuración local

1. Crear una base vacía llamada `vetturno` en MySQL.
2. Definir estas variables de entorno antes de iniciar la aplicación:

```text
DB_USER=tu_usuario_mysql
DB_PASSWORD=tu_password_mysql
JWT_SECRET=una_clave_local_de_al_menos_32_caracteres
```

Opcionalmente puede definirse `DB_URL`; si no existe, se usa:

```text
jdbc:mysql://localhost:3306/vetturno
```

No publiques contraseñas ni la clave JWT en GitHub.

## Ejecutar

```bash
mvn spring-boot:run
```

## Swagger

Con la aplicación iniciada:

```text
http://localhost:8080/swagger-ui.html
```

Registro y login son públicos. Para las demás rutas, copia el token devuelto por login y úsalo en **Authorize** como Bearer JWT.

## Endpoints principales

| Método | Ruta | Acceso |
|---|---|---|
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/login` | Público |
| POST | `/api/propietarios` | USER / ADMIN |
| GET | `/api/propietarios` | USER / ADMIN |
| POST | `/api/mascotas` | USER / ADMIN |
| GET | `/api/mascotas` | USER / ADMIN |
| POST | `/api/veterinarios` | ADMIN |
| GET | `/api/veterinarios` | USER / ADMIN |
| POST | `/api/citas` | USER / ADMIN |
| GET | `/api/citas` | USER / ADMIN |
| GET | `/api/citas/veterinario/{id}` | USER / ADMIN |

## Primer ADMIN

El registro público siempre crea usuarios con rol `USER`. Para la prueba académica, después de registrar a la administradora se cambia su rol de forma controlada en MySQL:

```sql
UPDATE usuario
SET rol = 'ADMIN'
WHERE email = 'admin@huellitas.com';
```

Después del cambio se debe iniciar sesión nuevamente para obtener un JWT que contenga el rol actualizado.

## Orden sugerido de prueba

1. Registrar un usuario.
2. Iniciar sesión.
3. Crear un propietario.
4. Crear una mascota con el `propietarioId` existente.
5. Probar que USER recibe 403 al crear veterinario.
6. Promover el usuario a ADMIN e iniciar sesión nuevamente.
7. Crear un veterinario.
8. Crear una cita futura.
9. Intentar repetir el mismo veterinario, fecha y hora para comprobar el rechazo.
10. Consultar la agenda completa y el filtro por veterinario.

## Nota sobre IA

Se utilizó IA como apoyo para estructurar y revisar el proyecto. Las decisiones se contrastaron con las guías de las clases 9 a 12 y con el contrato funcional del taller VetTurno.
