#!/bin/bash

docker-compose down
docker-compose up -d

sleep 15

asadmin --user admin --passwordfile pass --port=44848 deploy --upload=true myear/target/myear-1.0-SNAPSHOT.ear