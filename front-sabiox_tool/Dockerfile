FROM node:20-alpine AS build

# Define o diretório de trabalho
WORKDIR /app

# 1. Copia arquivos de dependência e configuração principal
COPY package*.json ./
COPY quasar.config.js ./
COPY index.html ./
# NOVO: Copia o arquivo de configuração do ESLint
COPY eslint.config.js ./

# 2. Copia a pasta 'src/'
COPY src/ ./src/

# 3. Instala as dependências
RUN npm install --legacy-peer-deps
RUN npm install dompurify jsdom --legacy-peer-deps
RUN npm install --legacy-peer-deps

# 4. Copia o restante do código-fonte
COPY public/ ./public/ 

# 5. Executa o build final do Quasar
RUN npx quasar build

# Segunda Stage: Nginx
FROM nginx:alpine AS final
# Copia o resultado do build Quasar para o Nginx (o caminho padrão de build é dist/spa)
COPY --from=build /app/dist/spa /usr/share/nginx/html

# Copia o arquivo de configuração do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
# O CMD padrão do Nginx já aponta para esse config
CMD ["nginx", "-g", "daemon off;"]
