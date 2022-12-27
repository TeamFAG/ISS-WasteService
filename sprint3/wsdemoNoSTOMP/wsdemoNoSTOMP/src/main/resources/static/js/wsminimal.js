var socket = connect();

function connect(){
    var host     = document.location.host;
    var pathname =  document.location.pathname;
    var addr     = "ws://" + host + pathname + "socket"  ;

    // Assicura che sia aperta un unica connessione
    if(socket!==undefined && socket.readyState!==WebSocket.CLOSED){
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    var socket = new WebSocket(addr); //CONNESSIONE

    socket.onopen = function (event) {
        console.log("Connected...")
    };
    socket.onmessage = function (event) { //RICEZIONE
        handleMsg(event.data)
    };
    return socket;
}//connect

function handleMsg(message) {
    message.toString().includes("MAXGB") ? handleSetupMsg(message) : handleStateMsg(message)
}

function handleSetupMsg(message) {
    const newGuiSetup = JSON.parse(message)
    updateMaxQt(newGuiSetup["MAXGB"], newGuiSetup["MAXPB"])
}

function handleStateMsg(message) {
    const newGuiState = JSON.parse(message)
    updatePosition(newGuiState["trolleyPosition"])
    updateGlass(newGuiState["glassContainerState"])
    updatePlastic(newGuiState["plasticContainerState"])

    switch (newGuiState["ledState"]) {
        case "OFF":
            ledOff()
            break
        case "ON":
            ledOn()
            break
        case "BLINKING":
            ledBlink()
            break
    }
}

