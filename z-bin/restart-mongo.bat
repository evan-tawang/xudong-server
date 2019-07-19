@echo off

set HOME=D:\im\support\mongodb
set LOG_DIR=D:\im\logs\mongo\

echo Begin restart......

cd %LOG_DIR%

rd /S /Q %LOG_DIR%

call %HOME%\bin\mongod --dbpath "%HOME%\data\db" --logpath "%LOG_DIR%log.log"  --install -serviceName "MongoDB"