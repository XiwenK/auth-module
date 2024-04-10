# VueJS + Spring Security + OAuth2 + JWT + PostgreSQL template project (Draft)
This is a template project for a VueJS frontend with a Spring Boot backend. The backend is secured with Spring Security and OAuth2. The frontend is secured with JWT. The backend uses PostgreSQL as a database.

## Stack
- Java 11
- VueJS frontend (Quasar)
- Spring Boot backend (v2.5.2)
- Spring Security (v5.5.1)
- OAuth2 (Google)
- JWT
- PostgreSQL (v13.3)
- Liquibase

## Prerequisites
### Database setup
1. Install PostgreSQL or use a Docker container (see `docker-compose.yml`).
2. Create a database named `template` with a user `template` and password `template`. Command:
```shell
psql -U postgres -c "CREATE DATABASE template;"
psql -U postgres -c "CREATE USER template WITH ENCRYPTED PASSWORD 'template';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE template TO template;"
```

### Google OAuth2 setup
1. Go to the [Google Cloud Console](https://console.cloud.google.com/).
2. Create a new project.
3. Go to the `APIs & Services` -> `Credentials` section.
4. Create a new OAuth Client ID. 
6. Add `http://localhost:your_frontend_port/login` as Authorized JavaScript origins. Keep in mind the `your_frontend_port` must be the same as the frontend port that defined in the `quasar.conf.js` file, which is `9000` by default.
7. Add `http://localhost:your_backend_port/api/v1/login/oauth2/code/google` Authorized redirect URIs. Here port `your_backend_port` is the backend port that defined in the `application.yaml` file, which is `9090` by default.
8. Copy the `Client ID` and `Client Secret` and add them to environment variables GOOGLE_CLIENT_ID and GOOGLE_CLIENT_SECRET. The `application.yaml` file is already configured to read these environment variables.


## Build
### Build backend
Run the following command in the root directory of the project:
```shell
maven clean install
```

### Build frontend
Run the following command in the root directory of the project:
```shell
cd src/main/vue
npm install
```

## Run
### Backend
Run the following command in the root directory of the project:
```shell
java -jar target/auth-0.0.1-SNAPSHOT.jar
```

### Frontend
```shell
cd src/main/vue
quasar dev
```

## Usage
### Login with Google
1. Open the frontend in your browser: `http://localhost:9000/login`.
2. Click on the `Login with Google` button.
3. You will be redirected to the Google login page.
4. After successful login, you will be redirected back to the frontend.

### Register
1. Open the frontend in your browser: `http://localhost:9000/register`.
2. Fill in the form and click on the `Register` button.
3. You will be redirected to the login page.
4. Login with the credentials you just registered.

