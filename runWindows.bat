@echo off
setlocal

if not exist bin mkdir bin

javac -cp "src;h2-2.4.240.jar" -d bin src\*.java
java -cp "bin;h2-2.4.240.jar" App

