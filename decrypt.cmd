@echo off

java -Xdebug -Xrunjdwp:transport=dt_socket,address=8453,server=y,suspend=n -classpath target\aes-1.0-SNAPSHOT.jar ca.ulaval.pul.FileDecryption %*

