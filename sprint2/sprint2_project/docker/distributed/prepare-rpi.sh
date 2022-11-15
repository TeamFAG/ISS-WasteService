
IP="192.168.50.13"

JAR_PATH="../../build/libs/it.unibo.ctxrasp.MainCtxraspKt-1.0.jar"
DIST_FOLDER="/home/pi/Sviluppo/wsdistr"
CHECK=""

log() {
    printf "################################################################################\n"
    printf "%s\n" "$@"
    printf "################################################################################\n"
}

copy_resources() {
  #ssh pi@$IP ls /home/pi/Sviluppo | grep wsdistr > CHECK

  #if [[ "$CHECK" =~ "wsdistr" ]]; then
  #  log Distribution folder already exist... deleting
  #  ssh pi@$IP rm -r /home/pi/Sviluppo/wsdistr
  #fi

  log Copying distribution files

  ssh pi@$IP mkdir /home/pi/Sviluppo/wsdistr
  scp $JAR_PATH pi@$IP:~/Sviluppo/wsdistr
  scp ./sprint2_project.pl pi@$IP:~/Sviluppo/wsdistr
  scp ./sysRules.pl pi@$IP:~/Sviluppo/wsdistr
  scp ./SystemConfiguration.json pi@$IP:~/Sviluppo/wsdistr
  scp ../../resources/wsLed/led25GpioTurnOff.sh pi@$IP:~/Sviluppo/wsdistr
  scp ../../resources/wsLed/led25GpioTurnOn.sh pi@$IP:~/Sviluppo/wsdistr
  scp ../../resources/wsSonar/SonarAlone.c pi@$IP:~/Sviluppo/wsdistr
  ssh pi@$IP g++ SonarAlone.c -l wiringPi -o SonarAlone

  log Copy done
}

main() {
  copy_resources
}

main
