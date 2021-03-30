# server

> **Rest service written in java. Receives data on channels from the database and is looking for interblockchain paths using the Dijkstra algorithm. Also finds un-escrow routes.**

## Docker

Docker build:

`docker build -t starway-server:v1 .`

Docker run:

`docker run --env APP_PORT=8085 --env DB_URL=jdbc:postgresql://<ip>:<port(default=5432)>/<db(default=starway_main_db)> --env DB_USER=<db_user> --env DB_PASS=<db_pass> -it -d --network="host" starway-server:v1`
