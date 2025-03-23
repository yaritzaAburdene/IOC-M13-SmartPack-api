#!/bin/bash
echo "Eliminando javadoc anterior..."
rm -rf src/main/resources/static/javadoc

echo "Generando JavaDoc..."
mvn javadoc:javadoc

echo "Copiando a static..."
mkdir -p src/main/resources/static/javadoc
cp -r target/reports/apidocs/* src/main/resources/static/javadoc/

echo "¡Hecho! Puedes acceder a http://localhost:8080/javadoc/index.html"
