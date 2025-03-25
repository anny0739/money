#!/bin/sh
current_dir=$(cd "$(dirname "$0")"; pwd)
db_container_name=mysqllocal
docker build --tag=$db_container_name $current_dir/docker/ && docker run -t -p=3306:3306 --name=db_container_name $db_container_name:latest
