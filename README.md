# server
`docker build -t starway-server:v1 .`

`docker run --env APP_PORT=8085  -it -d --network="host" starway-server:v1`

`docker run --env APP_PORT=8085 --env DB_URL=jdbc:postgresql://<ip>:<port(default=5432)>/<db(default=starway_main_db)> --env DB_USER=<db_user> --env DB_PASS=<db_pass> -it --network="host" starway-server:v1`
