@echo off
echo 该工程编译需要JDK5.0或以上版本，请保证已设置JAVA_HOME环境变量为JDK5安装目录
@echo off
if "%JAVA_HOME%" == "" goto error
@echo on

REM 更新构建信息
call ant.bat build-number
call cvs commit -m "Build: automatically increase build number during build.bat." version.properties

REM 构建发布包
call ant.bat %1
