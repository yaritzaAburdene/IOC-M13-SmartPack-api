#!/bin/bash
echo "Generando JavaDoc..."
mvn javadoc:javadoc

echo "Copiando a static..."
mkdir -p src/main/resources/static/javadoc
cp -r target/reports/apidocs src/main/resources/static/javadoc

echo "¡Hecho! Abre http://localhost:8080/javadoc/index.html"
