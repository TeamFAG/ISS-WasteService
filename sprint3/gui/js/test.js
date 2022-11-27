// QUANTITIES
var curGlassQt = 0;
var curPlastQt = 0;
var maxGlassQt = 150;
var maxPlastQt = 150;

const ledState = {OFF:0, ON:1, BLINK:2}
var ledCurState = ledState.OFF;

function init(){
  // HERE LOAD DATA FROM SERVER
  var gText = document.getElementById("glassText");
  gText.innerHTML = curGlassQt + "/" + maxGlassQt;

  var pText = document.getElementById("plasticText");
  pText.innerHTML = curPlastQt + "/" + maxPlastQt;
}

function updateGlass(newGlassQt) {
  if (newGlassQt > curGlassQt) {
    var bar = document.getElementById("glassBar");
    var text = document.getElementById("glassText");

    var height = curGlassQt;
    var id = setInterval(frame, 10);
    function frame() {
      if (height >= newGlassQt) {
        clearInterval(id);
        curGlassQt = newGlassQt;
        text.innerHTML = curGlassQt + "/" + maxGlassQt;
      } else {
        height++;
        var perc = height / maxGlassQt * 100;
        bar.style.height = perc + "%";
        text.innerHTML = height + "/" + maxGlassQt;
      }
    }
  }
}

function updatePlastic(newPlastQt) {
  if (newPlastQt > curPlastQt) {
    var bar = document.getElementById("plasticBar");
    var text = document.getElementById("plasticText");

    var height = curPlastQt;
    var id = setInterval(frame, 10);
    function frame() {
      if (height >= newPlastQt) {
        clearInterval(id);
        curPlastQt = newPlastQt;
        text.innerHTML = curPlastQt + "/" + maxPlastQt;
      } else {
        height++;
        var perc = height / maxPlastQt * 100;
        bar.style.height = perc + "%";
        text.innerHTML = height + "/" + maxPlastQt;
      }
    }
  }
}

function ledOff() {
  if(ledCurState !== ledState.OFF){
    var led = document.getElementById("led");

    led.className = "led-off"
    ledCurState = ledState.OFF
  }
}

function ledOn() {
  if(ledCurState !== ledState.ON){
    var led = document.getElementById("led");

    led.className = "led-on"
    ledCurState = ledState.ON
  }
}

function ledBlink() {
  if(ledCurState !== ledState.BLINK){
    var led = document.getElementById("led");

    led.className = "led-blink"
    ledCurState = ledState.BLINK
  }
}