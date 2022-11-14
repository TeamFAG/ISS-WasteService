const messageWindow = document.getElementById("messageArea")
const messageInput = document.getElementById("inputmessage")
const sendButton = document.getElementById("send")
var socket

const connect = () => {
    var host = document.location.host
    var pathname = document.location.pathname
    console.log(host)
    console.log(pathname)
    //var addr = "ws://" + host + pathname + "socket"
    var addr = "ws://localhost:9696" + pathname + "socket"

    if(socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione websocket gia stabilita")
    }

    var socket = new WebSocket(addr)

    socket.onopen = (event) => {
    }

    socket.onmessage = (event) => {
        console.log(event.data)
    }

    return socket
}

socket = connect()

sendButton.onclick = (event) => {
    sendMessage(messageInput.value)
    messageInput.value = ""
}

const sendMessage = (message) => {
    if(socket.readyState !== 1) {
        console.log("printo qualcosa")
    }
    socket.send(message)
    addMessageToWindow("Sent Message: message " + message)
}

const addMessageToWindow = (message) => {
    messageWindow.innerHTML += `<div>${message}</div>`
}