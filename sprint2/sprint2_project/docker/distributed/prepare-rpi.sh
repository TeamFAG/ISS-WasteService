
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
  ssh pi@$IP ls /home/pi/Sviluppo | grep wsdistr > CHECK

  if [[ "$CHECK" =~ "wsdistr" ]]; then
    log Distribution folder already exist... deleting
    ssh pi@$IP rm -r /home/pi/Sviluppo/wsdistr
  fi

  log Copying distribution files

  ssh pi@$IP mkdir /home/pi/Sviluppo/wsdistr
  scp -P 2222 $JAR_PATH pi@$IP:~/Sviluppo/wsdistr
  scp -P 2222 ./sprint2_project.pl pi@$IP:~/Sviluppo/wsdistr
  scp -P 2222 ./SystemConfiguration.json pi@$IP:~/Sviluppo/wsdistr

  log Copy done
}

main() {
  copy_resources
}

main
