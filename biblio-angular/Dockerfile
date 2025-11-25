# Utiliser l'image NGINX officielle comme base
FROM nginx:latest

# Copier la configuration NGINX spécifique (à adapter si besoin)
COPY nginx-default.conf /etc/nginx/conf.d/default.conf

# Copier les fichiers compilés Angular dans le dossier attendu par NGINX
COPY ./dist/biblio-front/browser /usr/share/nginx/html

# Exposer le port 80 pour le frontend
EXPOSE 80

# Démarrage de NGINX au premier plan
CMD ["nginx", "-g", "daemon off;"]
