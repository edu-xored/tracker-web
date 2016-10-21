#!/bin/bash

#Simple .sh script, that launchs tracker-web-app and after booting local server opens
#new page with http://localhost:8080 adress in default browser. Session log writes
#into tracker-web-app.log file.

checkServerIsAlive () {
	if [[ $(ps | grep -o $PROC_ID) -ne $PROC_ID ]]; then
		echo "Server killed"
		return 1
	fi
	return 0
}

PATH_TO_JAR=$1
LOG_FILE="tracker-web-app.log"

if !(test -e "$PATH_TO_JAR"); then
	echo "File not found"
	exit 1
fi

java -jar "$PATH_TO_JAR" &>> "$LOG_FILE" &
PROC_ID=$!

checkServerIsAlive
SERVER_ALIVE=$?

if [[ $SERVER_ALIVE == 0 ]]; then
	echo "Starting the server"
	echo -e "\nStarting the server\n" &>> "$LOG_FILE"
else
	echo "Error while trying to start local server"
	exit 1
fi

while [[ $(curl -sL -w "%{http_code}\\n" http://localhost:8080 -o /dev/null) -eq "000" ]];
do
	checkServerIsAlive
	SERVER_ALIVE=$?
	if [[ $SERVER_ALIVE == 1 ]]; then
		echo "Error while trying to start local server"
		exit 1
	fi
	sleep 2
done

echo "Server started"
xdg-open http://localhost:8080 &
echo "Press any button to quit"
read -s -n 1
echo "Killing the server"
kill "$PROC_ID"

SERVER_ALIVE=0

while [[ $SERVER_ALIVE == 0 ]]; 
do
	sleep 1
	checkServerIsAlive
	SERVER_ALIVE=$?
done

echo "Server stopped"
echo -e "\nServer stopped\n" &>> "$LOG_FILE"
exit 0
