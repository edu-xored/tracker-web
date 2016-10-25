@echo off

:getPID  [RtnVar]
::
:: Store the Process ID (PID) of the currently running script in environment variable
:: RtnVar. If called without any argument, then simply write the PID to stdout.
::
setlocal disableDelayedExpansion
:getLock
set "lock=%temp%\%~nx0.%time::=.%.lock"
set "uid=%lock:\=:b%"
set "uid=%uid:,=:c%"
set "uid=%uid:'=:q%"
set "uid=%uid:_=:u%"
setlocal enableDelayedExpansion
set "uid=!uid:%%=:p!"
endlocal & set "uid=%uid%"
2>nul ( 9>"%lock%" (
  for /f "skip=1" %%A in (
    'wmic process where "name='cmd.exe' and CommandLine like '%%<%uid%>%%'" get ParentProcessID'
  ) do for %%B in (%%A) do set "PID=%%B"
  (call )
))||goto :getLock
del "%lock%" 2>nul
endlocal & (echo(%PID%) > %1
