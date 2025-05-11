#!/bin/bash
echo "Eliminant javadoc anterior..."
rm -rf src/main/resources/static/javadoc

echo "Generant JavaDoc..."
mvn javadoc:javadoc

echo "Copian a static..."
mkdir -p src/main/resources/static/javadoc
cp -r target/reports/apidocs/* src/main/resources/static/javadoc/

echo "Fet! Pots accedir a http://localhost:8080/javadoc/index.html"
