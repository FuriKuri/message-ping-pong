#!/bin/bash

docker service create --name game-client-ping --replicas 2 --network game-network -e SPRING_PROFILES_ACTIVE=ping -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 furikuri/game-client
docker service create --name game-client-pong --replicas 2 --network game-network -e SPRING_PROFILES_ACTIVE=pong -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 furikuri/game-client
docker service create --name game-controller --replicas 2 --network game-network -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 furikuri/game-controller
docker service create --name game-proxy -p 8080:80 --mode global --network game-network furikuri/game-proxy

docker service create --name=viz -p 8081:8080 --mode global --constraint=node.role==manager --mount=type=bind,src=/var/run/docker.sock,dst=/var/run/docker.sock manomarks/visualizer