#!/bin/sh

logFile=/home/root/bootCount.tt

TTY_LOGFILE=/home/root/ttyerror.tt
TTY_TEMPFILE=/home/root/ttymxc.txt
function TTY_test
{
	# /dev/$UART_PORT

  if [ ! -e "${TTY_LOGFILE}" ]; then

    echo "Create log files " > ${TTY_LOGFILE}
  fi

  for((i=0;i<=7;i++)) do
  	UART_PORT=ttyUSB$i
  	chmod o+rw /dev/$UART_PORT
  	if [ -f ${TTY_TEMPFILE} ]; then
  	   rm ${TTY_TEMPFILE}
  	fi

  	stty -F /dev/$UART_PORT 1200 cs7 -parodd parenb -cstopb -icanon -iexten -ixon -ixoff crtscts cread clocal echo -echoe echok -echoctl
  	sleep 2
  	cat /dev/$UART_PORT > ${TTY_TEMPFILE} &
  	cat_pid=$!
  	sync
  	echo "1234567890abcdefghijklmnopqrstuvwxyz!" >/dev/$UART_PORT
  	sleep 2
  	sync
  	get_data=$(head -n 1 ${TTY_TEMPFILE})
          sync
          kill $cat_pid &>/dev/null
        	ps &>/dev/null

  	if [ "$get_data" == "1234567890abcdefghijklmnopqrstuvwxyz!" ];then
  		return 0
  	else
        logNu=`cat ${logFile}`
        echo "testtimes${logNu}: /dev/ttymxc$i Error [$(date)] " >> ${TTY_LOGFILE}
    fi
 done
}

ETH_LOGFILE=/home/root/eth0error.tt
WALN_LOGFIL=/home/root/wlan0error.tt
function Lan_test
{

  if [ ! -e "${ETH_LOGFILE}" ]; then
       echo "Create log files " > ${ETH_LOGFILE}
  fi
  if [ ! -e "${WALN_LOGFIL}" ]; then
       echo "Create log files" > ${WALN_LOGFIL}
  fi

	ifconfig | grep wlan0 &> /dev/null
	if [ $? -eq 1 ]; then
		ifconfig wlan0 up
	fi

	ServiceCheck=`systemctl list-unit-files | grep wpa_supplicant.service  | awk '{print $2}'`
	if [[ "$ServiceCheck" == "disable" ]]; then
	  systemctl enable wpa_supplicant.service
           systemctl restart wpa_supplicant.service
	fi

	ifconfig | grep eth0 &> /dev/null
	if [ $? -eq 1 ]; then
		ifconfig eth0 up
	fi
	sleep 10
  ifconfig | grep wlan0 &> /dev/null
	if [ $? -eq 1 ]; then
    logNu=`cat ${logFile}`
			echo "testtimes${logNu}: wlan0 Error $(date) " >> ${WALN_LOGFILE}
	fi
        ifconfig | grep eth0 &> /dev/null
	if [ $? -eq 1 ]; then
    logNu=`cat ${logFile}`
			echo "testtimes${logNu}: eth0 Error $(date) " >> ${ETH_LOGFILE}
	fi
}
Lan_test
TTY_test
