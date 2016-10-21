#!/bin/bash

PATH_TO_JAR=$1

if !(test -e $PATH_TO_JAR); then
	echo "File not found"
	exit 1
fi

echo "Server started"
echo -e "\nServer started\n" >> tracker-web-app.log

java -jar $PATH_TO_JAR >> tracker-web-app.log &
PROC_ID=$!

while [[ $(curl -sL -w "%{http_code}\\n" http://localhost:8080 -o /dev/null) -eq "000" ]]
do
	if [[ $(ps | grep -o $PROC_ID) -ne "$PROC_ID" ]]; then
		echo "Error while trying to start local server"
		exit 1
	fi
	sleep 2
done

xdg-open http://localhost:8080 &
echo "Press any button to quit"
read -s -n 1
kill $PROC_ID

while [[ $(ps | grep -o $PROC_ID) -eq "$PROC_ID" ]]; do
	sleep 1
done

echo "Server stopped"
echo -e "\nServer stopped\n" >> tracker-web-app.log
exit 0