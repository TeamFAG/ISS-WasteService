import socket

port = 8070 
host = '192.168.1.124' ##'192.168.1.62'
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

def connect(port) :
    print("Trying to connect to " + host + ":" + str(port))
    server_address = (host, port)      ##localhost
    sock.connect(server_address)    
    print("CONNECTED " , server_address)

def forward( message ) :
    print("forward ", message)
    msg = message + "\n"
    byt=msg.encode()    #required in Python3
    sock.send(byt)

def terminate() :
    sock.close()
    print("BYE")

def console() :  
    print("On/Off: ")
    v =  str( input() )
    print("INPUT" , v  )    
    while( v != "q"  ):
        if v == 'on':
            msg = "msg(turnOnLed, dispatch, python, led, turnOnLed(_), 1)"
            forward(msg)
        else:
            msg = "msg(turnOffLed, dispatch, python, led, turnOffLed(_), 1)"
            forward(msg)

        v = str(input() )      


connect(port)
console()
terminate()
