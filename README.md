# SmartPack API - IOC M13

## 📦 Descripción

SmartPack es una API desarrollada en Java con Spring Boot para gestionar envíos de paquetería de manera eficiente. Permite la gestión de usuarios, paquetes, transportistas y seguimiento de entregas.

## 🚀 Tecnologías Utilizadas

- Java 17
- Spring Boot
- MySQL
- JPA/Hibernate
- REST API

## 📥 Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/IOC-M13-SmartPack-api.git
   cd IOC-M13-SmartPack-api
   ```
2. Configurar la base de datos en `application.properties`.
3. Ejecutar la aplicación con:
   ```bash
   mvn spring-boot:run
   ```

## 📡 Endpoints Básicos

- `POST /auth/login` - Autenticación de usuarios.
- `GET /paquetes/{id}` - Obtener detalles de un paquete.
- `POST /paquetes` - Crear un nuevo paquete.
- `PUT /paquetes/{id}` - Actualizar un paquete.
- `DELETE /paquetes/{id}` - Eliminar un paquete.

## 📜 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

📌 **Nota:** Este proyecto forma parte de la asignatura M13 del IOC y está en desarrollo.

