# 🔧 Fase di build Angular
FROM node:18 as build

WORKDIR /app

COPY package*.json ./
RUN npm install --legacy-peer-deps --verbose

COPY . .
RUN npm run build --prod

# 🌐 Fase di deploy con NGINX
FROM nginx:stable-alpine

# Rimuovi il default.conf che mostra la welcome page
RUN rm /etc/nginx/conf.d/default.conf

# Copia la nostra configurazione personalizzata
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia i file statici Angular nella cartella pubblica
COPY --from=build /app/dist/sito_curriculum/browser/ /usr/share/nginx/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
