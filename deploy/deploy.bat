@echo off
echo 该工程编译需要JDK5.0或以上版本，请保证已设置JAVA_HOME环境变量为JDK5安装目录
@echo off
if "%JAVA_HOME%" == "" goto error
@echo on
"%JAVA_HOME%/bin/java" -cp lib/ant/ant.jar;lib/ant/ant-launcher.jar;lib/ant/ant-junit.jar;lib/junit/junit.jar;lib/clover/clover.jar;"%JAVA_HOME%/lib/tools.jar" org.apache.tools.ant.Main %1 dep-tmp
