from truck import * 

connect(8050)

START = 2
STOP = 20

def loop():
    for i in reversed(range(START, STOP)):
        evt = "msg(distance, event, python, sonarfilter, distance({}), 1)".format(i)
        print(i)
        emit(evt)
        time.sleep(0.5)

    for i in range(START, STOP):
        evt = "msg(distance, event, python, sonarfilter, distance({}), 1)".format(i)
        print(i)
        emit(evt)
        time.sleep(0.5)

def oneshot():
    evt = "msg(distance, event, python, sonarfilter, distance({}), 1)".format(1)
    emit(evt)
    time.sleep(5)
    evt = "msg(distance, event, python, sonarfilter, distance({}), 1)".format(11)
    emit(evt)

oneshot()
# loop()

terminate()
