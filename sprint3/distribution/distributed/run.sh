#!/bin/bash

# USAGE
# ./run.sh context tests
# context := trolley | wasteservice | all
# tests := test1 | test2 | test3

trap "echo 'Interrupted';pkill -f 'it.unibo.ctxtrolley.MainCtxtrolleyKt-1.0.jar';pkill -f 'it.unibo.ctxwasteservice.MainCtxwasteserviceKt-1.0.jar';exit" INT

SPRINT1_DIR=../../../sprint1/sprint1_project
SPRINT2_DIR=../../../sprint2/sprint2_project
SPRINT3_DIR=../../../sprint3/sprint3_project

TEST_RESULTS=()

declare -a TEST_SPRINT1=(
    "TestUtilityMethods.testGetMapCoord"
    "TestUtilityMethods.testGetClosestCoord"
    "TestWasteservice.testLoadAcceptedPlastic"
    "TestWasteservice.testLoadAcceptedGlass"
    "TestWasteservice.testLoadRejectedPlastic"
    "TestWasteservice.testLoadRejectedGlass"
    "TestTrolleyMover.A_testHomeToIndoor"
    "TestTrolleyMover.B_testIndoorToPlasticbox"
    "TestTrolleyMover.C_testPlasticboxToHome"
    "TestTrolleyMover.D_testIndoorToGlassbox"
    "TestTrolleyMover.E_testGlassboxToHome"
    "TestTrolleyMover.F_testGlassToHomeInterruptedToIndoor"
    "TestTransporttrolley.testPlasticDeposit"
    "TestTransporttrolley.testGlassDeposit"
    "TestTransporttrolley.testPlasticGlassDeposit"
    "TestTransporttrolley.testGlassPlasticDeposit"
)

declare -a TEST_SPRINT2=(
    "UnitTestLed.testLedOn"
    "UnitTestLed.testLedOff"
    "UnitTestLed.testLedBlink"
    "IntegrationTestLed.integrationTestWithLed"
    "IntegrationTestLedHalt.integrationTestWithLedAndHalt"
)

declare -a TEST_SPRINT3=(
    ""
)

red() {
    echo -e "\033[31m$1\033[0m"
}

green() {
    echo -e "\033[32m$1\033[0m"
}
print_checkmark() {
  echo -e "\u2714"
}
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
    if [[ $1 == "test1" ]]
    then
        ARRAY=("${TEST_SPRINT1[@]}")
        path="$SPRINT1_DIR"
    elif [[ $1 == "test2" ]]
    then
        ARRAY=("${TEST_SPRINT2[@]}")
        path="$SPRINT2_DIR"
    else
        ARRAY=("${TEST_SPRINT3[@]}")
        path="$SPRINT3_DIR"
    fi

    cd "$path"

    i=0
    numFail=0

    for test in ${ARRAY[@]}; do
        echo "---------------------------- TEST " ${test} " ----------------------------"
        ./gradlew test --tests ${test}
        TEST_RESULTS[$i]=$?
        if [[ ${TEST_RESULTS[$i]} -ne 0 ]]; then
            ((numFail++))
            red "Test fallito :("
        else
            green "Test riuscito!"
        fi
        ((i++))
        echo "-------------------------------------------------------------------------"
    done

    i=0
    for test in ${ARRAY[@]}; do
        if [[ ${TEST_RESULTS[$i]} -eq 0 ]]; then
            green "${test} riuscito"
        else
            red "${test} fallito"
        fi
        ((i++))
    done
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
    cd pc
    run_context $1
elif [[ $1 == "test1" ]] || [[ $1 == "test2" ]] || [[ $1 == "test3" ]]
then
    run_test $1
else
    echo "Parametro non valido"
    echo "Usage ./run.sh [context] | [test]"
    echo "context := trolley | wasteservice | all"
    echo "test := test1 | test2 | test3"
    exit 1
fi
