@echo off
echo �ù��̱�����ҪJDK5.0�����ϰ汾���뱣֤������JAVA_HOME��������ΪJDK5��װĿ¼
@echo off
if "%JAVA_HOME%" == "" goto error
@echo on

REM ���¹�����Ϣ
call ant.bat build-number
call cvs commit -m "Build: automatically increase build number during build.bat." version.properties

REM ����������
call ant.bat %1
