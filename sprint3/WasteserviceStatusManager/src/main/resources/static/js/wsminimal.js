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
    console.log(message.toString())
    if (message.toString().includes("bean")) {
        handleRefreshMsg(message)
    }
    else if (message.toString().includes("MAXPB")) {
        handleSetupMsg(message)
    }
    else if (message.toString().includes("trolleystate")) {
        handleTrolleyStateMsg(message)
    }
    else if (message.toString().includes("trolleyposition")) {
        handleTrolleyPositionMsg(message)
    }
    else if (message.toString().includes("ledstate")) {
        handleLedStateMsg(message)
    }
    else if (message.toString().includes("plastic")) {
        handlePlasticStateMsg(message)
    }
    else if (message.toString().includes("glass")) {
        handleGlassStateMsg(message)
    }
}

function handleRefreshMsg(message) {
    handleGlassStateMsg(message)
    handlePlasticStateMsg(message)
    handleLedStateMsg(message)
    handleTrolleyStateMsg(message)
    handleTrolleyPositionMsg(message)
}

function handleSetupMsg(message) {
    const newGuiSetup = JSON.parse(message)
    updateMaxQt(newGuiSetup["MAXGB"], newGuiSetup["MAXPB"])
}


function handleTrolleyStateMsg(message) {
    const newGuiState = JSON.parse(message)
    updateState(newGuiState["trolleystate"])
}

function handleTrolleyPositionMsg(message) {
    const newGuiState = JSON.parse(message)
    updatePosition(newGuiState["trolleyposition"])
}

function handleLedStateMsg(message) {
    const newGuiState = JSON.parse(message)

    switch (newGuiState["ledstate"]) {
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

function handlePlasticStateMsg(message) {
    const newGuiState = JSON.parse(message)
    updatePlastic(newGuiState["plastic"])
}

function handleGlassStateMsg(message) {
    const newGuiState = JSON.parse(message)
    updateGlass(newGuiState["glass"])
}

