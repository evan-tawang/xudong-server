@echo off

set jvm=-Xms512M -Xmx512M
set LOG_DIR=D:/im/build/logs/
set service=xudong-im-service

set WORK_DIR=D:/im/workspace/xudong-server
set BUILD_DIR=D:\im\build\server\
set CONF_DIR=D:\im\conf

set mvn=D:/im/support/apache-maven-3.5.3/bin/mvn.cmd

echo Begin deploy......

cd %WORK_DIR%

git pull

cd %WORK_DIR%/%service%

call %mvn% clean deploy -Dmaven.test.skip=true

rd /s /q %BUILD_DIR%

xcopy target\%service%-1.0.jar %BUILD_DIR%
xcopy %CONF_DIR%\application.yml %BUILD_DIR%

cd %BUILD_DIR%

echo "java %jvm% -DLOG_DIR=%LOG_DIR% -jar %service%-1.0.jar"

java %jvm% -DLOG_DIR=%LOG_DIR% -jar %service%-1.0.jar







