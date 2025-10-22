# Guía de Postman para Users API

## Descripción
Esta colección de Postman contiene todas las pruebas necesarias para la API de usuarios desarrollada con Spring Boot. Incluye operaciones CRUD, autenticación y casos de prueba para validaciones.

## Configuración Inicial

### 1. Iniciar la aplicación Spring Boot
Antes de usar la colección, asegúrate de que tu API esté ejecutándose:

```bash
En la terminal, navega a la ruta del proyecto y dentro nejecutas:
mvn spring-boot:run
```

La aplicación debería estar disponible en `http://localhost:8080`

### 2. Importar archivos en Postman

1. Abre Postman
2. Haz clic en **"Import"** en la esquina superior izquierda
3. Selecciona **"Upload Files"**
4. Importa ambos archivos:
   - `Users-API-Collection.postman_collection.json`
   - `Users-API-Environment.postman_environment.json`

## Flujo de Pruebas Recomendado

### Flujo Básico:
1. **"Crear nuevo usuario"** - Crea un usuario de prueba
2. **"Obtener todos los usuarios"** - Verifica que se creó
3. **"Login"** - Autentica con el usuario creado
4. **"Actualizar usuario (PATCH)"** - Modifica algunos campos
5. **"Obtener usuario por ID"** - Verifica los cambios
6. **"Eliminar usuario"** - Limpia los datos de prueba

### Pruebas de Filtros:
1. Crear varios usuarios con datos diferentes
2. Probar diferentes filtros: `name+co+juan`, `email+sw+test`, etc.
3. Probar ordenamiento por diferentes campos

### Pruebas de Validación:
Ejecuta todos los requests en "Test Cases" para verificar que las validaciones funcionan correctamente.

## Variables de Entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `base_url` | URL base de la API | http://localhost:8080 |
| `test_email` | Email de prueba | juan.perez@example.com |
| `test_name` | Nombre de prueba | Juan Pérez García |
| `test_phone` | Teléfono de prueba | 5512345678 |
| `test_tax_id` | RFC de prueba | PEGJ890123ABC |
| `test_password` | Contraseña de prueba | miPassword123 |
| `user_id` | ID del usuario (se asigna automáticamente) | (vacío inicialmente) |

### 3. Configurar el entorno
1. En la esquina superior derecha, selecciona **"Users API Environment"**
2. Verifica que las variables estén configuradas correctamente:
   - `base_url`: http://localhost:8080
   - `test_email`: juan.perez@example.com
   - `test_name`: Juan Pérez García
   - `test_phone`: 5512345678
   - `test_tax_id`: PEGJ890123ABC
   - `test_password`: miPassword123

## Uso de la Colección

### Carpeta "Users CRUD"

#### 1. Obtener todos los usuarios
- **GET** `/users`
- No requiere parámetros
- Devuelve lista de todos los usuarios

#### 2. Obtener usuarios con filtro
- **GET** `/users?filter=campo%2Boperador%2Bvalor`
- **Operadores disponibles:**
  - `co`: contiene (contains)
  - `eq`: exacto (equals)
  - `sw`: empieza con (starts with)
  - `ew`: termina con (ends with)
- **Campos disponibles:** email, id, name, phone, tax_id, created_at
- **Ejemplo:** `name+co+juan` (busca usuarios que contengan "juan" en el nombre)

#### 3. Obtener usuarios ordenados
- **GET** `/users?sortedBy=campo`
- **Campos disponibles:** email, id, name, phone, tax_id, created_at

#### 4. Obtener usuario por ID
- **GET** `/users/{id}`
- Usa la variable `{{user_id}}` que se asigna automáticamente al crear un usuario

#### 5. Crear nuevo usuario
- **POST** `/users`
- Usa las variables de entorno para datos de prueba
- **Automáticamente guarda el ID** del usuario creado en `{{user_id}}`

#### 6. Actualizar usuario (PATCH)
- **PATCH** `/users/{id}`
- Permite actualizar campos específicos
- Usa el `{{user_id}}` del usuario creado

#### 7. Eliminar usuario
- **DELETE** `/users/{id}`
- Elimina el usuario usando `{{user_id}}`

### Carpeta "Authentication"

#### Login
- **POST** `/users/login`
- Usa `tax_id` como username
- Devuelve información del usuario autenticado

### Carpeta "Test Cases"
Contiene pruebas para casos de error:
- RFC inválido (debe devolver error 422)
- Teléfono inválido (debe devolver error 422)
- Credenciales incorrectas (debe devolver error 401)
- Usuario no encontrado (debe devolver error 404)

## Formato de Datos

### RFC (Tax ID)
- Formato: 4 letras + 6 dígitos + 3 caracteres alfanuméricos
- Ejemplo: `PEGJ890123ABC`

### Teléfono
- 10 dígitos: formato nacional mexicano
- 12 dígitos: debe empezar con 52 (código de país)
- Ejemplos: `5512345678` o `525512345678`

### Respuestas Esperadas

#### Usuario creado exitosamente (201):
```json
{
    "id": "uuid-generado",
    "email": "juan.perez@example.com",
    "name": "Juan Pérez García",
    "phone": "5512345678",
    "taxId": "PEGJ890123ABC",
    "createdAt": "22-10-2025 14:30",
    "passwordEnc": "contraseña-encriptada"
}
```

#### Login exitoso (200):
```json
{
    "message": "Login exitoso",
    "user": {
        "id": "uuid-del-usuario",
        "email": "juan.perez@example.com",
        "name": "Juan Pérez García",
        "phone": "5512345678",
        "taxId": "PEGJ890123ABC",
        "created_at": "22-10-2025 14:30"
    }
}
```

## Solución de Problemas

### Error: "Connection refused"
- Verifica que la aplicación Spring Boot esté ejecutándose
- Confirma que el puerto 8080 esté disponible

### Error 422: "tax_id debe tener formato RFC válido"
- Verifica que el RFC tenga el formato correcto: 4 letras + 6 dígitos + 3 alfanuméricos

### Error 422: "El telefono debe ser un número valido"
- Usa 10 dígitos para formato nacional o 12 dígitos empezando con 52

### Error 409: "El tax_id ya existe"
- Cambia el valor de `test_tax_id` en las variables de entorno
- O elimina el usuario existente antes de crear uno nuevo

