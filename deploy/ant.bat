@echo off
REM ���ű����ڵ���ant���ݱ���Ŀbuild�ļ�������Ŀ��������build.xml�ļ�
REM �ù��̱�����ҪJDK5.0�����ϰ汾���뱣֤������JAVA_HOME��������ΪJDK5��װĿ¼

REM ���ű����÷�������
REM call ant.bat deploy

@echo off
if "%JAVA_HOME%" == "" goto error

REM ����ant����
"%JAVA_HOME%/bin/java" -cp lib/ant/ant.jar;lib/ant/ant-nodeps.jar;lib/ant/ant-launcher.jar;lib/ant/ant-junit.jar;lib/junit/junit.jar;lib/clover/clover.jar;lib/ant/mysql-connector-java-5.0.7-bin.jar;"%JAVA_HOME%/lib/tools.jar" org.apache.tools.ant.Main %1
goto end

:error
echo ant.bat ��������JAVA_HOMEδ���壩

:end