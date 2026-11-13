Act4: API REST con Spring Boot y JWT

Proyecto de Jazmín Fernández López.

Es una API REST hecha con Spring Boot para administrar canciones. Una API es un programa que recibe peticiones y responde con datos en formato JSON, sin páginas web. Con ella se pueden crear, ver, listar, actualizar y eliminar canciones.

Tecnologías

Java 17, Spring Boot, Spring Security con JWT, Spring Data JPA y MySQL. Las pruebas se hicieron con Bruno.

API en el VPS

La API está funcionando en http://167.99.15.140:8089

Si se abre en el navegador sin token, muestra un error 401. Eso es correcto, porque significa que está protegida.

Cómo se usa

Primero se registra un usuario con una petición POST a /api/auth/register. Después se inicia sesión con POST a /api/auth/login, y la API devuelve un token. Ese token se manda en las demás peticiones, en el encabezado Authorization como Bearer Token. Con el token ya se pueden usar las rutas de canciones: /api/canciones para listar (con paginación) y crear, y /api/canciones/{id} para ver, actualizar y eliminar una canción.

Cómo funciona la seguridad

Al iniciar sesión, la API genera un token JWT, que es como una llave que demuestra quién eres. Un filtro de Spring Security revisa cada petición a las rutas protegidas. Si no trae token o el token no es válido, responde 401 y no deja pasar. Si el token es válido, la petición continúa.

DTOs y validación

Se usan DTOs para controlar qué datos entran y salen de la API. Por eso la contraseña del usuario nunca aparece en las respuestas. Los datos de entrada se validan con @Valid y Bean Validation. Si algo está mal, por ejemplo un email inválido, la API responde 400 con un mensaje que dice qué campo falló.

Despliegue en el VPS

Se compiló el proyecto en un archivo .jar y se subió al VPS en una carpeta nueva (/root/act4). Ahí se creó un archivo de configuración con el puerto 8089 . 

Bruno

En la carpeta bruno/ está la colección con todas las pruebas: registro, login, las operaciones de canciones, una petición sin token (que da 401) y un registro con email inválido (que da 400).
