rem windows
@echo off
set project_home=%~dp0
echo %project_home%
cd ..
set project_home=%cd%
echo %project_home%
setlocal enabledelayedexpansion
SET class_path=.
for /r %project_home%/lib %%i in (*.jar) do (
	rem echo %%i
	set class_path=!class_path!;%%i
)


for /r %project_home%/plugins %%i in (*.jar) do (
	rem echo %%i
	set class_path=!class_path!;%%i
)


for /r %project_home%/extlib %%i in (*.jar) do (
	rem echo %%i
	set class_path=!class_path!;%%i
)

echo %class_path%
java -Dlog4j.configurationFile=%project_home%/conf/support-log4j2.xml -classpath %class_path% com.weiwan.support.launcher.SupportAppClient
pause