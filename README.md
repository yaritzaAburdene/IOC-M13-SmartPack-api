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
3. Instal·lar els paquets:
```bash
mvn install
```
4. Executar l'aplicació amb:
```bash
mvn spring-boot:run
```
5. Netejar compila i empaqueta amb:
```bash
mvn clean package
```

## 📡 Endpoints Bàsics

- `POST /auth/login` - Autenticació d'usuaris.
- `POST /auth/registrar` - registrar usuaris.
- `GET /usuari/{id}` - Obtenir detalls d'un usuari.
- `POST /usuari` - Crear un nou usuari.
- `PUT /usuari/{id}` - Actualitzar un usuari.
- `DELETE /usuari/{id}` - Eliminar un usuari.

## 🧪 Test

1. Netejar test
```bash
mvn clean test
```
2. Executar tot el test 
```bash
mvn test
```
3. Executar només un fitxer test 
```bash
mvn -Dtest=UsuariServiceTest test
```

## 📒 JavaDoc
Per generar la documentació javadoc s'ha de executar un fitxer:

Executar des de Windows 🪟 amb powershell
```bash
.\copy-javadoc.cmd
```
Executar des de Ubuntu 🐧
```bash
.\copy-javadoc.sh
```
Si no te permís a Ubuntu 🐧
```bash
chmod +x copy-javadoc.sh
```

## 📜 Llicència

Aquest projecte està sota la llicència MIT. Consulta l'arxiu `LICENSE` per a més detalls.

---

📌 *Nota:* Aquest projecte forma part de l'assignatura M13 del IOC i està en desenvolupament.
