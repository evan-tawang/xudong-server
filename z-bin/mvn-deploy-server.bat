@echo off

set jvm=$1
set LOG_DIR=D:/im/workspace/build/logs/
set service=xudong-im-service

set WORK_DIR=D:/im/workspace/xudong-server
set BUILD_DIR=D:/im/workspace/build/
set CONF_DIR=D:/im/conf/

set mvn=D:/im/support/apache-maven-3.5.3/bin/mvn.cmd
set java=C:/Program Files/Java/jdk1.8.0_221/bin/java.exe

echo Begin deploy......

if [ ! -n "$jvm" ]; then
    jvm="-Xms512M -Xmx512M"
fi

cd %WORK_DIR%

git pull

cd %WORK_DIR%/%service%

%mvn% clean deploy -Dmaven.test.skip=true

copy target/%service%-1.0.jar %BUILD_DIR%/server/
copy %CONF_DIR%/application.yml %BUILD_DIR%/server/

echo "java %jvm% -DLOG_DIR=%LOG_DIR% -jar %service%-1.0.jar >/dev/null &"

nohup %java% %jvm% -DLOG_DIR=%LOG_DIR% -jar %service%-1.0.jar >/dev/null &

cmd

