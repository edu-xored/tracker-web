#!/bin/bash

#Simple .sh script, that launchs tracker-web-app and after booting local server opens
#new page with http://localhost:8080 adress in default browser. Session log writes
#into tracker-web-app.log file.

checkServerIsAlive () {
	if [[ $(ps | grep -o $proc_id) -ne $proc_id ]]; then
		echo "Server killed"
		return 1
	fi
	return 0
}

arg_number=$#

if [[ $arg_number -eq 0 ]]; then
	echo "Script argument not found, please specify the path to tracker-web-app jar file"
	read PATH_TO_JAR
elif [[ $arg_number -eq 1 ]]; then
	PATH_TO_JAR=$1
else
	echo "Unallowable number of arguments"
	echo "Press q to exit, Space or Return to specify the path to tracker-web-app jar file"
	PRESSED_KEY="null"
	while [[ "$PRESSED_KEY" != "q" && "$PRESSED_KEY" != "$(echo -e $'\n')" ]]; do
		read -s -n 1 PRESSED_KEY
	done
	if [[ $PRESSED_KEY == "q" ]]; then
		echo "Closing the program"
		exit 0
	elif [[ $PRESSED_KEY == "$(echo -e $'\n')" ]]; then
		echo -e "\nSpecify the path to tracker-web-app jar file"
		read PATH_TO_JAR
	fi
fi

log_file="tracker-web-app.log"

if !(test -e "$PATH_TO_JAR"); then
	echo "File not found"
	exit 1
fi

java -jar "$PATH_TO_JAR" &>> "$log_file" &
proc_id=$!

checkServerIsAlive
server_alive=$?

if [[ $server_alive -eq 0 ]]; then
	echo "Starting the server"
	echo -e "\nStarting the server\n" &>> "$log_file"
else
	echo "Error while trying to start local server"
	exit 1
fi

while [[ $(curl -sL -w "%{http_code}\\n" http://localhost:8080 -o /dev/null) -eq 000 ]]; do
	checkServerIsAlive
	server_alive=$?
	if [[ $server_alive -eq 1 ]]; then
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
kill $proc_id

server_alive=0

while [[ $server_alive -eq 0 ]]; do
	sleep 1
	checkServerIsAlive
	server_alive=$?
done

echo "Server stopped"
echo -e "\nServer stopped\n" &>> "$log_file"
exit 0
