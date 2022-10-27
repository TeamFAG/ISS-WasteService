from truck import * 

connect(8050)

START = 2
STOP = 20

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

terminate()
