::Simple .bat script, that launchs tracker-web-app and after booting local server opens
::new page with http://localhost:8080 adress in default browser. Session log writes
::into tracker-web-app.log file.
@echo off
set "name=%date:.=%-%time::=%%~x1"
set log=tracker-web-app.log
set TCP="%temp%\TCP_ports%name:,=%.tmp"
set PIDtmp="%temp%\PID%name:,=%.tmp"

if "%1"=="" (
	echo No parameters, set path to the .jar file
	set /p filepath="Path: "
) else (
	set filepath=%1
)

if not exist %filepath% (
	echo File not found
	echo File not found >>%log%
	pause >nul
	exit /B 1
) 

echo Starting server...
start "tracker-web-app" /i /min cmd /c "get-cmd-pid.bat %PIDtmp% & java -jar %filepath% >%log% 2>&1"
timeout /T 1 /NOBREAK >nul

findstr /B "Error:" %log%
if %ERRORLEVEL%==0 (
	del /F %PIDtmp%
	pause > nul
	exit /B 1
)

:loop
netstat -an -p TCP >%TCP%
findstr "0.0.0.0:8080" %TCP% >nul
if not %ERRORLEVEL%==0 (
	timeout /T 1 /NOBREAK >nul
	goto :loop
)
del /F %TCP%

start http://localhost:8080

echo Press any button to quit
set /p PID=< %PIDtmp%
pause > nul
taskkill /pid %PID% > nul
del /F %PIDtmp%
echo Server stopped
exit /B 0
