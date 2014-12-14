@echo off
REM 本脚本用于自动构建用于发布的bizyi包，依赖于build.xml文件
REM 该工程编译需要JDK5.0或以上版本，请保证已设置JAVA_HOME环境变量为JDK5安装目录

REM 本脚本调用方法范例
REM set CVSROOT=:pserver:deploy@172.16.0.80:/cvsroot
REM cvs checkout bizyi
REM call bizyi\deploy\autobuild.bat

@echo off
if "%JAVA_HOME%" == "" goto error

REM 设置发布目录前缀（根据分支/版本设置）
set RELEASE_PREFIX=R2

REM 设置工作主目录
set BUILD_ROOT=\bizyi_build

REM 切换到工作主目录
cd %BUILD_ROOT%

REM 进入deploy工作目录
cd bizyi\deploy

REM 更新构建信息
@echo on
call ant.bat build-number
call cvs commit -m "AutoBuild: automatically increase build number during autobuild.bat." version.properties
@echo off

REM 执行发布包构建程序
@echo on
call ant.bat autodeploy
@echo off

REM 确定发布目录，注意附加空格为时间解析时的分割符
for /f "tokens=1" %%a in ('date /t') do set RELEASE_DIR=%%a
for /f "tokens=1-2 delims=: " %%a in ('time /t ') do set RELEASE_DIR=%RELEASE_DIR%_%%a%%b

set RELEASE_DIR=%RELEASE_PREFIX%_%RELEASE_DIR%

REM 复制发布包到发布目录
echo 建立发布目录：%BUILD_ROOT%\daily\%RELEASE_DIR%
md %BUILD_ROOT%\daily\%RELEASE_DIR%
xcopy %BUILD_ROOT%\bizyi\deploy\dist %BUILD_ROOT%\daily\%RELEASE_DIR%  /E /H /I

echo *
echo * AUTOBUILD 脚本执行完成
echo *
goto end

:error
echo *
echo * AUTOBUILD 脚本执行错误：JAVA_HOME环境变量没有设置
echo *

:end
REM 返回工作主目录
cd %BUILD_ROOT%
