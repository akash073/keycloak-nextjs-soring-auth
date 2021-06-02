# keycloak-nextjs-spring-auth

## Spring Boot run
mvn spring-boot:run

## Keycloak command to run
1. Go to keycloak folder
2. To start run

sudo docker build -t my-keycloak .
 
sudo docker run -dp 8000:8080 my-keycloak

3. To stop the docker image

sudo docker stop $(sudo docker ps -q --filter ancestor='my-keycloak' )
