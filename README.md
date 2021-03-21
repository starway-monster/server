# server
`docker build -t starway-server:v1 .`

`docker run --env APP_PORT=8085  -it -d --network="host" starway-server:v1`