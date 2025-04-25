#!/bin/bash
# --------------------------------------------
# Configuración Docker Compose
export DOCKER_COMPOSE_FILE=./docker-compose.yml
export DOCKER_NETWORK=host  # Usando network_mode: host

# --------------------------------------------
# Configuración PostgreSQL Container
export DB_HOST=localhost  # Cuando usas network_mode: host
export DB_PORT=5432
export DB_USER=postgres
export DB_PASSWORD=abc123  # Actualizado para coincidir con tu compose
export DB_NAME=postgres    # Postgres crea una DB llamada 'postgres' por defecto

# --------------------------------------------
# Configuración PGAdmin
export PGADMIN_URL=http://localhost:5050
export PGADMIN_EMAIL=sl20016@ues.edu.sv
export PGADMIN_PASSWORD=abc123

# --------------------------------------------
# Configuración OpenLiberty (ajustada)
export LIBERTY_HOME=/usr/local/openliberty/wlp10_24_0_0_8
export SERVER_NAME=tpi135_2025
export SERVER_DIR=$LIBERTY_HOME/usr/servers/$SERVER_NAME
export WAR_FILE=$SERVER_DIR/dropins/PupaSV-1.0-SNAPSHOT.war

# --------------------------------------------
# Variables para operación
export LIBERTY_BIN=$LIBERTY_HOME/bin/server
export LOG_FILE=$SERVER_DIR/logs/messages.log
