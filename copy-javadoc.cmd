@echo off
echo Eliminat javadoc anterior...
if exist src\main\resources\static\javadoc (
    rmdir /S /Q src\main\resources\static\javadoc
)

echo Generant JavaDoc...
call mvn clean javadoc:javadoc

echo Copian a static...
mkdir src\main\resources\static\javadoc 2>nul
xcopy /E /Y target\reports\apidocs\* src\main\resources\static\javadoc\

echo Fet! Pots accedir a http://localhost:8080/javadoc/index.html
pause