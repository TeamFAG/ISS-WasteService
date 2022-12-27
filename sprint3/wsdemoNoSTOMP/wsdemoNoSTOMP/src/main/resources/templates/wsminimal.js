var socket = connect();

function connect(){
    var host     = document.location.host;
    var pathname =  document.location.pathname;
    var addr     = "ws://localhost:8085"; // +host + pathname + "socket"  ;

    // Assicura che sia aperta un unica connessione
    if(socket!==undefined && socket.readyState!==WebSocket.CLOSED){
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    var socket = new WebSocket(addr); //CONNESSIONE

    socket.onopen = function (event) {
        addMessageToWindow("Connected");
    };
    socket.onmessage = function (event) { //RICEZIONE
        addMessageToWindow(`Got Message: ${event.data}`);
    };
    return socket;
}//connect

const messageWindow   = document.getElementById("messageArea");
const messageInput    = document.getElementById("inputmessage");
const sendButton      = document.getElementById("send");

sendButton.onclick = function (event) {
    sendMessage(messageInput.value);
    messageInput.value = "";
}
function sendMessage(message) {
    socket.send(message);
    addMessageToWindow("Sent Message: " + message);
}
function addMessageToWindow(message) {
    messageWindow.innerHTML += `<div>${message}</div>`
}