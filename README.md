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

## Run without SSL
sudo docker run --name key -d -p 8000:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin jboss/keycloak


sudo docker exec -it keycloak bash

cd /opt/jboss/keycloak/bin/

./kcadm.sh config credentials --server http://localhost:8080/auth --realm master --user admin  --password admin

./kcadm.sh update realms/master -s sslRequired=NONE
