# Matriz de pruebas manuales

Completar esta tabla únicamente con resultados reales obtenidos durante la ejecución de VetTurno.

| # | Escenario | Resultado esperado | Resultado real | Evidencia |
|---|---|---|---|---|
| 1 | La aplicación inicia con MySQL disponible | Servidor activo y esquema accesible | Pendiente | Pendiente |
| 2 | Registro válido de Paula | 200 y token; contraseña hasheada | Pendiente | Pendiente |
| 3 | Registro con email inválido y clave corta | 400 con errores por campo | Pendiente | Pendiente |
| 4 | Login con credenciales válidas | 200 y JWT vigente | Pendiente | Pendiente |
| 5 | GET /api/citas sin token | Acceso rechazado | Pendiente | Pendiente |
| 6 | POST /api/veterinarios con USER | 403 Forbidden | Pendiente | Pendiente |
| 7 | POST /api/veterinarios con ADMIN | 201 y veterinario persistido | Pendiente | Pendiente |
| 8 | Creación válida de propietario | 201 y DTO sin colecciones anidadas | Pendiente | Pendiente |
| 9 | Creación de mascota con propietario existente | 201 y relación correcta | Pendiente | Pendiente |
| 10 | Mascota con propietario inexistente | 400 controlado; no se inserta fila | Pendiente | Pendiente |
| 11 | Cita futura con referencias válidas | 201 y cita persistida | Pendiente | Pendiente |
| 12 | Cita con fecha pasada | 400 con mensaje claro | Pendiente | Pendiente |
| 13 | Segundo intento con mismo veterinario y horario | 400; se conserva una sola cita | Pendiente | Pendiente |
| 14 | Filtro de citas por veterinario | 200 y solo coincidencias | Pendiente | Pendiente |
| 15 | Reinicio y prueba desde Swagger con Authorize | Datos persisten y flujo protegido funciona | Pendiente | Pendiente |
