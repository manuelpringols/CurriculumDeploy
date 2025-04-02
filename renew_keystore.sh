#!/bin/bash

# Configura le variabili
DOMAIN="manuelpringols.info"
EMAIL="manuelpringols@gmail.com"
KEYSTORE_PASSWORD="Jhonnyxvendetta2"
KEYSTORE_PATH="/root/sito_curriculum/CurriculumDeploy/backend/src/main/resources/keystore.p12"
DOCKER_CONTAINER_NAME="curriculumdeploy-backend-1"

echo "🔄 [1] Rinnovo del certificato Let's Encrypt..."
certbot certonly --standalone -d $DOMAIN -d www.$DOMAIN --non-interactive --agree-tos --email $EMAIL --force-renewal

echo "🔄 [2] Creazione del keystore PKCS12..."
openssl pkcs12 -export \
  -inkey /etc/letsencrypt/live/$DOMAIN/privkey.pem \
  -in /etc/letsencrypt/live/$DOMAIN/fullchain.pem \
  -out $KEYSTORE_PATH \
  -password pass:$KEYSTORE_PASSWORD \
  -name tomcat

echo "🔄 [3] Aggiornamento permessi del keystore..."
chmod 600 $KEYSTORE_PATH

echo "🔄 [4] Copia del nuovo keystore nel container Docker..."
docker cp $KEYSTORE_PATH $DOCKER_CONTAINER_NAME:/app/src/main/resources/keystore.p12

echo "🔄 [5] Riavvio del container Docker..."
docker restart $DOCKER_CONTAINER_NAME

echo "✅ Rinnovo completato con successo!"
