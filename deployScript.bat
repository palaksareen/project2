D:\softwares\apache-tomcat-8.5.9-windows-x64\apache-tomcat-8.5.9\bin\shutdown.bat
del /f  D:\softwares\apache-tomcat-8.5.9-windows-x64\apache-tomcat-8.5.9\logs\*
del /f D:\softwares\apache-tomcat-8.5.9-windows-x64\apache-tomcat-8.5.9\webapps\puk.war
rmdir /f  D:\softwares\apache-tomcat-8.5.9-windows-x64\apache-tomcat-8.5.9\webapps\puk
del /f  D:\logs\getpuk\*
cp D:\projects\Online_PUK\build\libs\puk.war D:\softwares\apache-tomcat-8.5.9-windows-x64\apache-tomcat-8.5.9\webapps

D:\softwares\apache-tomcat-8.5.9-windows-x64\apache-tomcat-8.5.9\bin\startup.bat