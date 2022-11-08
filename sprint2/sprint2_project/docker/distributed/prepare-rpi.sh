
IP="192.168.50.13"

JAR_PATH="../../build/libs/it.unibo.ctxrasp.MainCtxraspKt-1.0.jar"

log() {
    printf "################################################################################\n"
    printf "%s\n" "$@"
    printf "################################################################################\n"
}

copy_resources() {
  ssh pi@$IP mkdir ~/Sviluppo/wsdistr
  #scp -P 2222 $JAR_PATH pi@$IP:~/Sviluppo/wsdistr
  #scp -P 2222  pi@$IP:~/Sviluppo/wsdistr
}

main() {
  copy_resources
}

main
