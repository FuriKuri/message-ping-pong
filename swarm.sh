#!/bin/bash

docker network create --driver overlay game-network

docker service create --name zookeeper1 --publish 2181:2181 -e ZOO_MY_ID="1" -e ZOO_SERVERS="server.1=0.0.0.0:2888:3888 server.2=zookeeper2:2888:3888 server.3=zookeeper3:2888:3888" --network game-network zookeeper
docker service create --name zookeeper2 -e ZOO_MY_ID="2" -e ZOO_SERVERS="server.1=zookeeper1:2888:3888 server.2=0.0.0.0:2888:3888 server.3=zookeeper3:2888:3888" --network game-network zookeeper
docker service create --name zookeeper3 -e ZOO_MY_ID="3" -e ZOO_SERVERS="server.1=zookeeper1:2888:3888 server.2=zookeeper2:2888:3888 server.3=0.0.0.0:2888:3888" --network game-network zookeeper

docker service create --name kafka --replicas 3 --network game-network -e KAFKA_ADVERTISED_PORT=9092 -e KAFKA_CREATE_TOPICS="ping:4:1,pong:4:1,controller:4:1" -e KAFKA_ZOOKEEPER_CONNECT="zookeeper1:2181,zookeeper2:2181,zookeeper3:2181" wurstmeister/kafka

docker service create --name redis-master --network game-network redis:3-alpine
docker service create --name redis-slave --network game-network redis:3-alpine redis-server --slaveof redis-master 6379
docker service create --name redis-sentinel --network game-network -e SENTINEL_DOWN_AFTER=5000 -e SENTINEL_FAILOVER=5000 furikuri/redis-sentinel

#sleep 15

#docker service scale kafka=2
