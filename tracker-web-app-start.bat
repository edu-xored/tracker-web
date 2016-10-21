@echo off
if "%1"=="" (echo No parameters, set path to the file. & set /p filepath="Path: ") else (set filepath=%1)
if not exist %filepath% (@echo File not found. & pause > nul & goto end) 
echo Starting server...
start "tracker-web-app" /i /min cmd /c "java -jar %filepath% > tracker-web-app.log"
timeout /T 6 /NOBREAK > nul
start http://localhost:8080
echo Press any button to quit
pause > nul
taskkill /fi "windowtitle eq tracker-web-app" /im cmd.exe>nul
:end