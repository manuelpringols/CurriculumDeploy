FROM node:latest as build

WORKDIR /app
COPY package*.json ./

RUN npm install
COPY . .
RUN npm run build --prod

#/app/dist/project_name
FROM bitnami/apache:latest
COPY --chown=1001:1001 --from=build /app/dist/sito_curriculum/* /opt/bitnami/apache/htdocs 
COPY --chown=1001:1001 certss /opt/bitnami/apache/conf/bitnami/certs
COPY --chown=1001:1001 config /opt/bitnami/apache/conf/bitnami
