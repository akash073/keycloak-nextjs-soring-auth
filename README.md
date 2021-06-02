# keycloak-nextjs-spring-auth


# Run all docker files

Run All Using - 

sudo docker-compose up


## NextJs project run
Go to directory and run

npm run dev


## Spring Boot run
mvn spring-boot:run

## Keycloak command to run
1. Go to keycloak folder
2. To start run

sudo docker build -t my-keycloak .
 
sudo docker run -dp 8000:8080 my-keycloak

3. To stop the docker container

sudo docker stop $(sudo docker ps -q --filter ancestor='my-keycloak' )
