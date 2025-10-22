# Users API
API REST desarrollada con **Spring Boot** para la gestión de usuarios.  
Permite crear, consultar, actualizar y eliminar usuarios, así como autenticarse mediante login cifrado.

---

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 17** o superior  
- **Maven 3.9+**  
- **Git** (opcional, para clonar el repositorio, ver el versionamiento y commits con buenas practicas) "Recomendado"

---

## Ejecución del proyecto

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/anfony/users-api.git
   cd users-api

2. **Compila el proyecto**
     ``` bash
     mvn clean install
2.1 **Configura la variable de entorno AES_SECRETKEY**
La aplicación utiliza una clave AES para cifrar y descifrar contraseñas, definida en:
    app:
      security:
        aes-secret: ${AES_SECRETKEY:dGVzdGluZzEyMzQ1Njc4OTBhYmNkZWZnaGlqa2xtbm8=}
        
Puedes definir tu propia clave secreta o usar la recomendada para pruebas.
En Windows (PowerShell):
```
$env:AES_SECRETKEY = "YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWE="
```
O Puedes generar tu propia clave de 32 bytes en Base64 con:
``` bash
    openssl rand -base64 32
```

3. **Ejecuta la aplicación**
    ``` bash
        mvn spring-boot:run
Una vez iniciada, la API estará disponible en: http://localhost:8080

Endpoints principales

| Método | Endpoint       | Descripción                                                  |
| :----- | :------------- | :----------------------------------------------------------- |
| GET    | `/users`       | Obtiene todos los usuarios (permite filtros y ordenamientos) |
| GET    | `/users/{id}`  | Obtiene un usuario por su UUID                               |
| POST   | `/users`       | Crea un nuevo usuario                                        |
| PATCH  | `/users/{id}`  | Actualiza parcialmente un usuario                            |
| DELETE | `/users/{id}`  | Elimina un usuario                                           |
| POST   | `/users/login` | Autentica un usuario por `tax_id` y contraseña               |

Pruebas con Postman
En la carpeta /postman encontrarás:

Users-API-Collection.postman_collection.json -> colección de endpoints

Users-API-Environment.postman_environment.json -> variables de entorno

README.md -> instrucciones detalladas de prueba

Para probar:

1.- Abre Postman
2.- Importa ambos archivos (Collection y Environment)
3.- Configura la variable {{base_url}} con http://localhost:8080
4.- Ejecuta los endpoints desde la colección
