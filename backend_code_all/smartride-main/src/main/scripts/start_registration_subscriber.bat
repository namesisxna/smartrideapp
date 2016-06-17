
REM -- Booking Subscriber Laucher -----

set pwd=%CD%

cd ..\..\..\target

java -classpath smartride-main-1.0.jar -Dhttp.proxyHost=proxy.cognizant.com -Dhttp.proxyPort=6050 com.cts.gto.techngage.smartride.backend.main.LaunchRegistration

cd %pwd%
