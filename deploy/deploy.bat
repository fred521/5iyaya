@echo off
echo �ù��̱�����ҪJDK5.0�����ϰ汾���뱣֤������JAVA_HOME��������ΪJDK5��װĿ¼
@echo off
if "%JAVA_HOME%" == "" goto error
@echo on
"%JAVA_HOME%/bin/java" -cp lib/ant/ant.jar;lib/ant/ant-launcher.jar;lib/ant/ant-junit.jar;lib/junit/junit.jar;lib/clover/clover.jar;"%JAVA_HOME%/lib/tools.jar" org.apache.tools.ant.Main %1 dep-tmp
