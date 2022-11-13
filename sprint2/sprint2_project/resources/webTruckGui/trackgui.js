
var material = document.getElementById('material')
var quantity = document.getElementById('quantity')
var textarea = document.getElementById('textarea')
var requestButton = document.getElementById('request')
var connectButton = document.getElementById('connect')

var isConnected = false

// connection
connectButton.onclick = () => {
    const socket = new WebSocket('ws://127.0.0.1:8050')

    socket.addEventListener('open', (event) => {
        isConnected = true
        textarea.innerHTML = "Connected"
        console.log('connected')
    })
}

requestButton.onclick = () => {
    console.log('ciao')
    // material.value
}