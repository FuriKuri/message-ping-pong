#!/bin/bash

docker service create --name game-client-ping --replicas 2 --network game-network -e SPRING_PROFILES_ACTIVE=ping -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 furikuri/game-client
docker service create --name game-client-pong --replicas 2 --network game-network -e SPRING_PROFILES_ACTIVE=pong -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 furikuri/game-client
docker service create --name game-controller --network game-network -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 --publish 8080:8080 furikuri/game-controller
