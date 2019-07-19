@echo off

set WORK_DIR=D:/im/workspace/xudong-web
set BUILD_DIR=D:/im/workspace/build
set HOST=http://47.96.89.84:9003

echo Begin build......

cd %WORK_DIR%

git pull

call npm install

call webpack --config vue.portal.config.js --host %HOST%

copy build/ ${BUILD_DIR}/portal/

cmd