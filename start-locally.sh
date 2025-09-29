#!/bin/sh
docker compose down file-delivery-service && ./gradlew :rs:bootJar && docker compose build && docker compose up -d