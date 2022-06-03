# Data Pipeline
Esta aplicación consume los datos de una API de metrobuses de la ciudad de México, y en conjunto con
Google Maps vincula la ubicación con la Alcaldia correspondiente para dicha ciudad.

## ¿Qué necesito para que funcione?
1. Jdk11
2. Docker
3. Docker compose
4. Kubernetes (otra forma de desplegar)
5. Minikube
6. Paciencia :C

## ¿Cómo desplegar esta aplicación con docker-compose?
1. Clona el repositorio en tu computadora
2. Accede a la carpeta con el siguiente comando: **cd tu_carpeta**
3. Genera el archivo .jar del proyecto spring (se anda buscando una mejor forma y mejor optimizada) utilizando el
   comando: **./mvnw clean package** o **mvn clean package**
4. Si ya tienes tu carpeta target, ahora puedes proceder a lanzar el combo: **docker-compose up --build** o simplemente **docker-compose up**

## ¿Cómo desplegar esta aplicación con Kubernetes?
1. Clonar el repositorio en tu computadora
2. Accede a la carpeta con el siguiente comando: **cd tu_carpeta**
3. Genera el archivo .jar del proyecto spring (se anda buscando una mejor forma y mejor optimizada) utilizando el
   comando: **./mvnw clean package** o **mvn clean package**
4. Genera la imagen docker del proyecto con **docker build -t spring-k8s:latest**
5. Desplegar la base de datos con **kubectl apply -f bd-deployment.yaml**, el archvo se encuentra en la raíz del proyecto.
6. Para verificar que la base de datos haya sido dsplegada usa **kubectl get deployments** y **kubectl get pods**
7. Desplegar la aplicación con **kubectl apply -f app-deployment.yaml**, el archvo se encuentra en la raíz del proyecto.
