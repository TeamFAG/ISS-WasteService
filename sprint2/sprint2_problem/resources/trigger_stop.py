from socket import close
from truck import * 

connect(8050)

evt = "msg(trigger, dispatch, python, sonarinterfacemock, trigger(_), 1)"
forward(evt)

terminate()
