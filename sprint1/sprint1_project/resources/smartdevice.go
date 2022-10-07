package main

import (
	"net"
	"os"
)

func main() {
	msg := "msg(storeRequest, request,python,wasteservice,storeRequest(GLASS,5),1)"

	address := "localhost:8050"
	tcpAddr, err := net.ResolveTCPAddr("tcp", address)
	if err != nil {
		println("ResolveTCPAddr faile: ", err.Error())
		os.Exit(1)
	}

	conn, err := net.DialTCP("tcp", nil, tcpAddr)
	if err != nil {
		println("Dial failed: ", err.Error())
		os.Exit(1)
	}

	_, err = conn.Write([]byte(msg))
	if err != nil {
		println("Write failed: ", err.Error())
		os.Exit(1)
	}

	println("Sended: ", msg)

	reply := make([]byte, 1024)
	_, err = conn.Read(reply)
	if err != nil {
		println("Read failed: ", err.Error())
		os.Exit(1)
	}

	println("Reply: ", string(reply))

	conn.Close()
}
