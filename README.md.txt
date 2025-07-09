# BCI Java Assessment - User Registration API

RESTful API for user registration developed as part of the BCI Java Specialist Integration assessment.

## Technologies Used

- Java 17
- Spring Boot
- Spring Security with JWT
- Spring Data JPA
- HSQLDB Database (in-memory)
- Maven
- JUnit/Mockito
- Swagger/OpenAPI 3

## Features

- User registration with email and password validation
- JWT token generation and authentication
- Phone number management
- Comprehensive error handling
- Email format validation
- Configurable password validation
- H2 in-memory database
- Unit and integration tests
- API documentation with Swagger

## Configurando el proyecto

### Prerequisitos

- Java 17
- Maven 3.6 or higher

### Installation

1. Clonar el repositorio:
```bash
git clone https://github.com/cerritosc/bci.git
cd bci

2. Build the project:

mvn clean compile

3. Ejecutar los tests:

mvn test

4. Iniciar la aplicación:

mvn spring-boot:run

La aplicación iniciará en http://localhost:8080







# Documentación y Pruebas de la API

Este proyecto incluye documentación completa de la API usando Swagger/OpenAPI 3.0, lo que facilita explorar y probar todos los endpoints disponibles.

## Acceso a la Documentación de la API

Una vez que la aplicación esté ejecutándose, se puede acceder a la documentación interactiva de la API en:

- Swagger UI: http://localhost:8080/swagger-ui/index.html

## Controladores Disponibles

La documentación de la API incluye los siguientes controladores:

### 1. Controlador de Usuarios (`/api/users`)
- GET `/api/users` - Obtener usuario loggeado

### 2. Controlador de Autenticación (`/auth`)
  Registro de usuarios
- POST `/auth/register` - Registrar un nuevo usuario

## Probando la API con Swagger UI

### Paso 1: Acceder a Swagger UI
Navega a http://localhost:8080/swagger-ui/index.html en tu navegador.

### Paso 2: Probando Endpoints (Registro de usuario)
1. Expande la API auth-controller haciendo clic en ella
2. Expande el endpoint /auth/register haciendo clic en él
2. Haz clic en "Try it out"
3. Completa los parámetros requeridos
4. Haz clic en "Execute" para enviar la petición
5. Revisa la respuesta, verifica que se el token sea parte de la respuesta ya que sirve para autenticar los demás endpoints

## Ejemplo: Probando Usuario creado

### Paso 1: Autenticación (Token Bearer)
La mayoría de los endpoints requieren autenticación para probar endpoints protegidos, por lo que se puede seguir los siguientes pasos:

1. Haz clic en el botón "Authorize" 🔒 en la parte superior derecha de Swagger UI
2. Ingresa el token creado en el endpoint /auth/register (sección anterior) en el formato:

   ```
   Ejemplo: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3MuY2Vycml0b3NAc21hcnRqb2IuY29tIiwiaWF0IjoxNzUxOTgyNTQ4LCJleHAiOjE3NTIwNjg5NDh9.oQ-jKbSlzNEkcYMkjHMQ9klXSocUPqKFf17_tBLEdHU
   ```
   O simplemente agregalo en el input de la Ventana emergente:
   ```
   Ejemplo: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3MuY2Vycml0b3NAc21hcnRqb2IuY29tIiwiaWF0IjoxNzUxOTgyNTQ4LCJleHAiOjE3NTIwNjg5NDh9.oQ-jKbSlzNEkcYMkjHMQ9klXSocUPqKFf17_tBLEdHU
   ```

3. Haz clic en "Authorize" y luego en "Close"
4. Todas las llamadas posteriores a la API incluirán el header de autenticación


### Paso 2: Consultado el usuario loggeado (Token Bearer del paso 1)
1. Navega a la sección Controlador de Usuarios (API user-controller)
2. Encuentra el endpoint GET `/api/users`
3. Haz clic en "Try it out"
4. No es necesario enviar payload
5. Haz clic en "Execute"
6. Revisa la respuesta

Formato de respuesta

{
  "name": "string",
  "email": "string",
  "created": "date",
  "modified": "date",
  "lastLogin": "date"
}


## Formatos de Respuesta

Para el caso solicitado la respuesta se obtiene de la siguiente forma:

Respuesta Exitosa:
```json
{
  "id": "string",
  "created": "string",
  "modified": "date",
  "lastLogin": "date",
  "token": "string",
  "active": boolean
}
```

Respuesta de Error:
```json
{"mensaje": "mensaje de error"}
```

## Payload sugerido para endpoint /auth/register

{
  "name": "Juan Perez",
  "email": "juan.perez@smartjob.com",
  "password": "ClaveSegura123.",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}


