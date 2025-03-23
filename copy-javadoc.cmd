@echo off
echo Eliminando javadoc anterior...
if exist src\main\resources\static\javadoc (
    rmdir /S /Q src\main\resources\static\javadoc
)

echo Generando JavaDoc...
call mvn clean javadoc:javadoc

echo Copiando a static...
mkdir src\main\resources\static\javadoc 2>nul
xcopy /E /Y target\reports\apidocs\* src\main\resources\static\javadoc\

echo ¡Hecho! Puedes acceder a http://localhost:8080/javadoc/index.html
pause