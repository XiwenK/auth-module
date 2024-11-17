# VueJS + Spring Security + OAuth2 + JWT + PostgreSQL template project (Draft)
This is a template project for a VueJS frontend with a Spring Boot backend. The backend is secured with Spring Security and OAuth2. The frontend is secured with JWT. The backend uses PostgreSQL as a database.

## Stack
- Java 21
- VueJS (Quasar)
- Spring Boot 3.3.5
- Spring Security 6.3.4
- PostgreSQL
- Liquibase
- Docker

## Prerequisites
### Install Docker Desktop
1. Go to the [Docker Desktop](https://www.docker.com/products/docker-desktop) website.
2. Download and install Docker Desktop.
3. Run Docker Desktop.

### Database setup
1. Run docker-compose to start a PostgreSQL database (database will be available by URL `jdbc:postgresql://localhost:5433/postgres`, username `postgres`, password `example`):
```shell
docker-compose up -d
```
2. Copy the username and password and add them to environment variables `POSTGRES_USER` and `POSTGRES_PASSWORD`. The `application.yaml` file is already configured to read these environment variables.

### JWT secret setup
1. Set any JWT key to the environment variable JWT_SECRET. The `application.yaml` file is already configured to read this environment variable.

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

### Install frontend
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
Run the following command in the root directory of the project:
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

