##Redis Broker

En este ejemplo vamos a usar redis con broker, es decir, comunicación entre dos o varios microservicios

### Redis CLI

Enviar y recivir mensaje desde linea de comandos de redis primero es arranca un redis con el siguiente comando:

```shell script
docker-compose up -d
```

Nos conectamos al container, arrancamos una cli de redis y nos subcribimos a un topic :

```shell script
docker exec -it redis sh
redis-cli
SUBSCRIBE payments
```

Nos conectamos otra vez al container, arrancamos una cli de redis y publicamos un mensaje en el topic :

```shell script
docker exec -it redis sh
redis-cli
PUBLISH payments "Hi redis"
```