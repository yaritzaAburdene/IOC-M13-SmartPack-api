# SmartPack API - IOC M13

## 📦 Descripció

SmartPack és una API desenvolupada a Java amb Spring Boot per a gestionar enviaments de paqueteria de manera eficient. Permet la gestió d'usuaris, paquets, transportistes i seguiment de lliuraments.

## 🚀 Tecnologies Utilitzades

- Java 17
- Spring Boot
- MySQL
- JPA/*Hibernate
- REST API

## 📥 Instal·lació

1. Clonar el repositori:
```bash
git clone https://github.com/tu-usuario/ioc-m13-smartpack-api.git
cd IOC-M13-SmartPack-api
```
2. Configurar la base de dades en `application.properties`.
3. Executar l'aplicació amb:
```bash
mvn spring-boot:run
```
4. Netejar compila i empaqueta amb:
```bash
mvn clean package
```

## 📡 Endpoints Bàsics

- `POST /auth/login` - Autenticació d'usuaris.
- `POST /auth/registrar` - registrar usuaris.
- `GET /paquets/{id}` - Obtenir detalls d'un paquet.
- `POST /paquets` - Crear un nou paquet.
- `PUT /paquets/{id}` - Actualitzar un paquet.
- `DELETE /paquets/{id}` - Eliminar un paquet.

## 📜 Llicència

Aquest projecte està sota la llicència MIT. Consulta l'arxiu `LICENSE` per a més detalls.

---

📌 *Nota:* Aquest projecte forma part de l'assignatura M13 del IOC i està en desenvolupament.
