# Simulador de un foro implementado en Java y Node

#### Para levantar las bases de datos en docker:

``docker run -d -p 3306:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=forum mysql:8``

``docker run -d -p 27017:27017 --name mongodb -e MONGO_INITDB_DATABASE=forum mongo:5.0.3``