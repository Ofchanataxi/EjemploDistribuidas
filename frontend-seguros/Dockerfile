# Etapa 1: Build
FROM node:20-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production

# Etapa 2: Nginx
FROM nginx:alpine
# Copiamos el build de Angular a la carpeta de Nginx
COPY --from=build /app/dist/frontend-seguros/browser /usr/share/nginx/html
# Copiamos la configuración personalizada de Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]