Proyecto Literalura - Catálogo de Libros
Hola! Este es mi proyecto del Challenge Literalura. Es una aplicación hecha en Java para buscar libros de una API (Gutendex) y guardarlos en mi base de datos de PostgreSQL.

¿Qué usé para hacerlo?
Java 17: El lenguaje de programación.

Spring Boot: Para que todo el proyecto funcione más rápido.

PostgreSQL: Para guardar los libros y que no se borren.

JPA / Hibernate: Para conectar Java con la base de datos.

API de Gutendex: De aquí saco la información de los libros.

¿Qué hace mi programa?
Cuando corres el programa, te sale un menú en la consola con estas opciones:

Buscar libro por título: Escribes el nombre del libro y el programa lo busca en internet. Si lo encuentra, lo guarda en la base de datos.

Listar libros registrados: Te muestra todos los libros que ya guardaste.

Listar autores registrados: Te muestra los autores que están en la base de datos.

Listar autores vivos en un año: Le das un año y te dice qué autores de los que guardaste estaban vivos en ese tiempo.

Listar libros por idioma: Puedes filtrar para ver solo los libros que están en español (es) o inglés (en), por ejemplo.

Configuración para que funcione
Para que el programa se conecte a la base de datos, hay que cambiar estos datos en el archivo src/main/resources/application.properties:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres
spring.datasource.password=PON_TU_CONTRASEÑA_AQUÍ

Autor
Frank Molina
