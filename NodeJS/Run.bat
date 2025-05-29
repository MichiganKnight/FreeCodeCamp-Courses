@echo off
setlocal enabledelayedexpansion

echo Listing .js Files in Current Folder
set /a count=0

rem Gather all .js files in current directory
for %%f in (*.js) do (
    set /a count+=1
    set "file[!count!]=%%f"
    echo !count!: %%f
)

if %count%==0 (
    echo No .js Files Found
    pause
    exit /b
)

echo.
echo Enter Number of File to Run:
set /p choice=

rem Check if choice is valid
if not defined file[%choice%] (
    echo Invalid Choice
    pause
    exit /b
)

set "filename=!file[%choice%]!"

echo.
echo Running: node "!filename!"
echo ---------------------------------
node "!filename!"

endlocal
pause