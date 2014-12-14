@echo off
REM ���ű������Զ��������ڷ�����bizyi����������build.xml�ļ�
REM �ù��̱�����ҪJDK5.0�����ϰ汾���뱣֤������JAVA_HOME��������ΪJDK5��װĿ¼

REM ���ű����÷�������
REM set CVSROOT=:pserver:deploy@172.16.0.80:/cvsroot
REM cvs checkout bizyi
REM call bizyi\deploy\autobuild.bat

@echo off
if "%JAVA_HOME%" == "" goto error

REM ���÷���Ŀ¼ǰ׺�����ݷ�֧/�汾���ã�
set RELEASE_PREFIX=R2

REM ���ù�����Ŀ¼
set BUILD_ROOT=\bizyi_build

REM �л���������Ŀ¼
cd %BUILD_ROOT%

REM ����deploy����Ŀ¼
cd bizyi\deploy

REM ���¹�����Ϣ
@echo on
call ant.bat build-number
call cvs commit -m "AutoBuild: automatically increase build number during autobuild.bat." version.properties
@echo off

REM ִ�з�������������
@echo on
call ant.bat autodeploy
@echo off

REM ȷ������Ŀ¼��ע�⸽�ӿո�Ϊʱ�����ʱ�ķָ��
for /f "tokens=1" %%a in ('date /t') do set RELEASE_DIR=%%a
for /f "tokens=1-2 delims=: " %%a in ('time /t ') do set RELEASE_DIR=%RELEASE_DIR%_%%a%%b

set RELEASE_DIR=%RELEASE_PREFIX%_%RELEASE_DIR%

REM ���Ʒ�����������Ŀ¼
echo ��������Ŀ¼��%BUILD_ROOT%\daily\%RELEASE_DIR%
md %BUILD_ROOT%\daily\%RELEASE_DIR%
xcopy %BUILD_ROOT%\bizyi\deploy\dist %BUILD_ROOT%\daily\%RELEASE_DIR%  /E /H /I

echo *
echo * AUTOBUILD �ű�ִ�����
echo *
goto end

:error
echo *
echo * AUTOBUILD �ű�ִ�д���JAVA_HOME��������û������
echo *

:end
REM ���ع�����Ŀ¼
cd %BUILD_ROOT%
