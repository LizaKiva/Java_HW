#!/bin/bash

set -e

AUTH_SERVICE_DIR="./authservice"
TICKET_ORDER_SERVICE_DIR="./ticketorder"

echo "Building Auth Service..."
cd $AUTH_SERVICE_DIR
sh ./mvnw clean install
cd ..

echo "Building Ticket Order Service..."
cd $TICKET_ORDER_SERVICE_DIR
sh ./mvnw clean install
cd ..


echo "Building Docker images..."
cd $AUTH_SERVICE_DIR
docker build -t authservice .
cd ..

cd $TICKET_ORDER_SERVICE_DIR
docker build -t ticketorder .
cd ..

echo "Starting services with Docker Compose..."
docker-compose up --build
