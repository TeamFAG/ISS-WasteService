#!/bin/bash

# USAGE
# ./run.sh context tests
# context := trolley | wasteservice | all
# tests := test1 | test2 | test3

trap "echo 'Interrupted';pkill -f 'it.unibo.ctxtrolley.MainCtxtrolleyKt-1.0.jar';pkill -f 'it.unibo.ctxwasteservice.MainCtxwasteserviceKt-1.0.jar';exit" INT

run_context() {
    if [[ $1 == "all" ]]
    then
        echo "Running all contexts in background"

        nohup java -jar it.unibo.ctxwasteservice.MainCtxwasteserviceKt-1.0.jar &
        nohup java -jar it.unibo.ctxtrolley.MainCtxtrolleyKt-1.0.jar &

    elif [[ $1 == "wasteservice" ]]
    then
        java -jar it.unibo.ctxwasteservice.MainCtxwasteserviceKt-1.0.jar &
    elif [[ $1 == "trolley" ]]
    then
        java -jar it.unibo.ctxtrolley.MainCtxtrolleyKt-1.0.jar &
    fi

    echo "Premere Ctrl+C per uscire"

    while true; do
        read -n1 -s
        if [[ $REPLY = "^C" ]]; then
            echo "Uscita richiesta"

            pkill -f "it.unibo.ctxtrolley.MainCtxtrolleyKt-1.0.jar"
            pkill -f "it.unibo.ctxwasteservice.MainCtxwasteserviceKt-1.0.jar"

            exit
        fi
    done
}

run_test() {
    echo "ciao"
}

if [[ $# -ne 1 ]]
then
    echo "Numero parametri non valido"
    echo "Usage ./run.sh [context] | [test]"
    echo "context := trolley | wasteservice | all"
    echo "test := test1 | test2 | test3"
    exit 1
fi

if [[ $1 == "trolley" ]] || [[ $1 == "wasteservice" ]] || [[ $1 == "all" ]]
then
    run_context $1
elif [[ $1 == "test1" ]] || [[ $1 == "test2" ]] || [[ $1 == "test2" ]]
then
    run_test $1
else
    echo "Parametro non valido"
    echo "Usage ./run.sh [context] | [test]"
    echo "context := trolley | wasteservice | all"
    echo "test := test1 | test2 | test3"
    exit 1
fi
