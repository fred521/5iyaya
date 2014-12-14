@echo off
REM 本脚本用于调用ant根据本项目build文件构建项目，依赖于build.xml文件
REM 该工程编译需要JDK5.0或以上版本，请保证已设置JAVA_HOME环境变量为JDK5安装目录

REM 本脚本调用方法范例
REM call ant.bat deploy

@echo off
if "%JAVA_HOME%" == "" goto error

REM 调用ant构建
"%JAVA_HOME%/bin/java" -cp lib/ant/ant.jar;lib/ant/ant-nodeps.jar;lib/ant/ant-launcher.jar;lib/ant/ant-junit.jar;lib/junit/junit.jar;lib/clover/clover.jar;lib/ant/mysql-connector-java-5.0.7-bin.jar;"%JAVA_HOME%/lib/tools.jar" org.apache.tools.ant.Main %1
goto end

:error
echo ant.bat 致命错误（JAVA_HOME未定义）

:end