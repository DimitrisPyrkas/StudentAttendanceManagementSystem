@echo off
echo Cleaning old build...
rmdir /S /Q build
del StudentAttendanceSystem.war

echo Compiling Java source files...
mkdir build\WEB-INF\classes

REM Compile ALL .java at once to keep packages intact
javac -d build\WEB-INF\classes -sourcepath src src\com\example\model\*.java src\com\example\storage\*.java src\com\example\servlet\*.java

echo Copying static files...
xcopy WebContent build /E /I

echo Creating WAR...
cd build
jar -cvf ..\StudentAttendanceSystem.war *

echo Done! WAR created: StudentAttendanceSystem.war
