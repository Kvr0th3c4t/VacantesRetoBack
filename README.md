# INNOVATECH - API REST

![Badge de Estado](https://img.shields.io/badge/Estado-Producción-green)
![Badge de Versión](https://img.shields.io/badge/Versión-v1.0-blue)
![Badge de Licencia](https://img.shields.io/badge/Licencia-MIT-green)

## 📖 Descripción

**INNOVATECH** es una API REST robusta para la gestión de vacantes de empleo y solicitudes de usuarios. Diseñada para simplificar y agilizar el proceso de contratación de personal para empresas y la búsqueda de empleo para los usuarios. Este sistema permite a las empresas publicar y gestionar vacantes, mientras que los usuarios pueden buscar y postularse a las oportunidades laborales disponibles.

## ✨ Características

- **Autenticación JWT** con access y refresh tokens
- **Gestión de roles** (Administrador, Empresa, Cliente)
- **CRUD completo** de vacantes, empresas y categorías
- **Sistema de solicitudes** con seguimiento de estado
- **Filtros avanzados** por empresa y categoría
- **Gestión de perfiles** para empresas y usuarios
- **API RESTful** con respuestas JSON estandarizadas
- **Documentación interactiva** con Swagger UI
- **Seguridad robusta** con validación de tokens

## 🚀 Demo

**🔗 API en producción:** [https://api-vacantes.adriancc.com/](https://api-vacantes.adriancc.com/)

**📋 Documentación Swagger:** Disponible en el servidor de desarrollo

## 📸 Endpoints Principales

### Autenticación
- `POST /auth/login` - Inicio de sesión
- `POST /auth/refresh` - Renovar token de acceso
- `POST /auth/registro` - Registro de nuevos usuarios

### Administración
- `GET /api/admin/verVacantes` - Ver todas las vacantes
- `POST /api/admin/altaEmpresa` - Crear nueva empresa
- `GET /api/admin/verEmpresas` - Listar empresas
- `POST /api/admin/altaCategoria` - Crear categoría

### Empresas
- `GET /api/empresa/verTodasVacantes` - Vacantes de la empresa
- `POST /api/empresa/altaVacante` - Publicar nueva vacante
- `PUT /api/empresa/modificarVacante/{id}` - Editar vacante
- `GET /api/empresa/verTodasSolicitudes` - Ver solicitudes recibidas

### Usuarios
- `GET /api/usuario/verVacanteCreada` - Ver vacantes disponibles
- `POST /api/usuario/postularVacante/{id}` - Postularse a vacante
- `GET /api/usuario/verSolicitudes` - Ver mis solicitudes
- `DELETE /api/usuario/solicitud/eliminar/{id}` - Eliminar solicitud

## 🧪 Credenciales de Prueba

Para probar todas las funcionalidades del sistema:

| Rol | Email | Password | Descripción |
|-----|-------|----------|-------------|
| 👨‍💼 **Administrador** | admin@empleoreto.com | admin123 | Gestión completa del sistema |
| 🏢 **RRHH/Empresa** | rrhh@tecnosoluciones.com | empresa123 | Publicar y gestionar vacantes |
| 👤 **Cliente** | juan.perez@mail.com | cliente123 | Buscar y aplicar a empleos |

### 🚀 Cómo probar:
1. **Accede a Swagger UI:** [https://api-vacantes.adriancc.com/swagger-ui/index.html](https://api-vacantes.adriancc.com/swagger-ui/index.html)
2. **Prueba el login** con cualquiera de las credenciales arriba
3. **Copia el token JWT** que obtienes en la respuesta
4. **Autorízate en Swagger** usando el botón "Authorize"
5. **Explora todos los endpoints** según tu rol

### 🌐 Frontend completo:
- **Aplicación web:** [https://vacantes.adriancc.com/](https://vacantes.adriancc.com/)
- Prueba la experiencia completa con las mismas credenciales

## 🛠️ Tecnologías utilizadas

### Backend Framework
- **Spring Boot:** Framework principal
- **Spring Security:** Seguridad y autenticación
- **Spring Data JPA:** Persistencia de datos

### Base de Datos
- **MySQL:** Base de datos relacional
- **Hibernate:** ORM para mapeo objeto-relacional

### Autenticación & Seguridad
- **JWT (JSON Web Tokens):** Autenticación stateless
- **BCrypt:** Encriptación de contraseñas

### Documentación & Testing
- **Swagger/OpenAPI:** Documentación interactiva de la API
- **Postman:** Testing y documentación de endpoints

### Utilidades
- **Lombok:** Reducción de código boilerplate
- **Jackson:** Serialización/deserialización JSON

### Deploy & Infrastructure
- **Servidor VPS con dominio personalizado**

## 📋 Prerrequisitos

Antes de comenzar, asegúrate de tener instalado:

- [Java JDK](https://www.oracle.com/java/technologies/downloads/) (versión 11 o superior)
- [Maven](https://maven.apache.org/) (versión 3.6 o superior)
- [MySQL](https://www.mysql.com/) (versión 8.0 o superior)
- [Git](https://git-scm.com/)

## ⚙️ Instalación

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/tuusuario/innovatech-api.git
   ```

2. **Navega al directorio del proyecto**
   ```bash
   cd innovatech-api
   ```

3. **Configura la base de datos**
   ```sql
   # Ejecuta el script incluido en el proyecto
   mysql -u root -p < db/vacantes_BBDD_2025_reto.sql
   
   # O manualmente:
   CREATE DATABASE vacantes_db;
   CREATE USER 'vacantes_user'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON vacantes_db.* TO 'vacantes_user'@'localhost';
   ```

4. **Configura application.properties**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/vacantes_db
   spring.datasource.username=vacantes_user
   spring.datasource.password=password
   
   jwt.secret=tu_clave_secreta_jwt
   jwt.access.expiration=3600000
   jwt.refresh.expiration=86400000
   ```

5. **Instala las dependencias**
   ```bash
   mvn clean install
   ```

6. **Ejecuta la aplicación**
   ```bash
   mvn spring-boot:run
   ```

7. **Accede a la documentación**
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## 🏗️ Estructura del proyecto

```
VacantesRetoBack/
├── .mvn/
│   └── wrapper/                           # Maven wrapper
├── db/
│   └── vacantes_BBDD_2025_reto.sql       # Script de base de datos
├── src/
│   ├── main/
│   │   ├── java/vacantes/
│   │   │   ├── configuracion/             # Configuraciones
│   │   │   │   ├── DataUserConfiguration.java
│   │   │   │   └── SwaggerConfig.java     # Configuración Swagger
│   │   │   ├── jwt/                       # Utilidades JWT
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── JwtTokenUtil.java
│   │   │   ├── modelo/
│   │   │   │   ├── dto/                   # Data Transfer Objects
│   │   │   │   ├── entities/              # Entidades JPA
│   │   │   │   └── service/               # Servicios de negocio
│   │   │   ├── repository/                # Repositorios JPA
│   │   │   │   ├── IGenericoCRUD.java
│   │   │   │   ├── CategoriaRepository.java
│   │   │   │   ├── EmpresaRepository.java
│   │   │   │   ├── SolicitudRepository.java
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   └── VacanteRepository.java
│   │   │   ├── restcontroller/            # Controladores REST
│   │   │   │   ├── AdminRestController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── EmpresaRestController.java
│   │   │   │   └── UsuarioRestController.java
│   │   │   └── Reto3Vacantes1Application.java # Clase principal
│   │   └── resources/
│   │       ├── META-INF/
│   │       └── application.properties
│   └── test/
│       └── java/vacantes/
│           └── Reto3Vacantes1ApplicationTests.java
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## 🔧 Scripts disponibles

```bash
mvn spring-boot:run    # Ejecuta la aplicación en modo desarrollo
mvn clean compile     # Compila el proyecto
mvn test              # Ejecuta las pruebas unitarias
mvn clean package     # Genera el JAR para producción
```

## 🔐 Autenticación

La API utiliza JWT (JSON Web Tokens) para la autenticación:

### Flujo de autenticación:
1. **Login**: `POST /auth/login` con email y password
2. **Respuesta**: Access token (1h) y Refresh token (24h)
3. **Uso**: Incluir `Authorization: Bearer {access_token}` en headers
4. **Renovación**: `POST /auth/refresh` con el refresh token

### Roles disponibles:
- **ADMON**: Administrador del sistema
- **EMPRESA**: Cuenta empresarial para publicar vacantes
- **CLIENTE**: Usuario final que busca empleo

## 📱 Endpoints por Rol

### 🔧 Administrador (`/api/admin`)
- Gestión completa de empresas, categorías y usuarios
- Visualización de todas las vacantes y solicitudes
- Creación de cuentas empresariales
- Moderación del contenido

### 🏢 Empresa (`/api/empresa`)
- Publicación y gestión de vacantes propias
- Revisión de solicitudes recibidas
- Aceptación/rechazo de candidatos
- Gestión del perfil empresarial

### 👤 Usuario (`/api/usuario`)
- Búsqueda y filtrado de vacantes disponibles
- Postulación a ofertas laborales
- Seguimiento de solicitudes enviadas
- Gestión de perfil personal

## 🚀 Deployment

La aplicación está desplegada en un servidor VPS:

### Configuración del servidor
- **OS:** Linux/Ubuntu Server
- **Java:** OpenJDK 11
- **Base de datos:** MySQL 8.0
- **Proxy reverso:** Nginx
- **SSL:** Certificado configurado
- **Dominio:** api-vacantes.adriancc.com

### Para deploy local
1. Ejecuta `mvn clean package`
2. El JAR se genera en `target/`
3. Ejecuta con `java -jar target/innovatech-api.jar`

## 📋 Formato de Respuestas

### Respuesta exitosa:
```json
{
  "mensaje": "Operación exitosa",
  "data": { ... }
}
```

### Respuesta de error:
```json
{
  "mensaje": "Descripción del error",
  "error": "UNAUTHORIZED"
}
```

## 🐛 Reportar problemas

Si encuentras algún bug o tienes sugerencias:

1. Verifica que no exista un issue similar
2. Crea un [nuevo issue](https://github.com/Kvr0th3c4t/innovatech-api/issues)
3. Proporciona toda la información relevante (logs, endpoints, payload)

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - mira el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Co-Autores

**Paula Fernández**
- Gihutb: https://github.com/Pafer10

**Andrés Matabuena**
- Github: https://github.com/DevYuco

**Adrián Carmona**
- Email: adrianc.crim@hotmail.com
- API: [api-vacantes.adriancc.com](https://api-vacantes.adriancc.com/)

## 🙏 Agradecimientos

- [Spring Boot](https://spring.io/projects/spring-boot) por el framework robusto
- [JWT.io](https://jwt.io/) por la especificación de tokens
- [Swagger](https://swagger.io/) por la documentación interactiva
- [MySQL](https://www.mysql.com/) por la base de datos confiable
- [Postman](https://www.postman.com/) por las herramientas de testing

## 📊 Estado del proyecto

![GitHub issues](https://img.shields.io/github/issues/Kvr0th3c4t/innovatech-api)
![GitHub pull requests](https://img.shields.io/github/issues-pr/Kvr0th3c4t/innovatech-api)
![GitHub stars](https://img.shields.io/github/stars/Kvr0th3c4t/innovatech-api)
![GitHub forks](https://img.shields.io/github/forks/Kvr0th3c4t/innovatech-api)

---

⭐️ **¡No olvides darle una estrella al proyecto si te gustó!** ⭐️

> **Nota:** Este es un proyecto personal desarrollado con fines educativos y profesionales. Toda la información de las vacantes es ficticia para propósitos de demostración.
