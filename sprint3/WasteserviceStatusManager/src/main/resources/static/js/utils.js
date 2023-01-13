// QUANTITIES
var curGlassQt = 0;
var curPlastQt = 0;
var maxGlassQt = 150;
var maxPlastQt = 150;

// LED
const ledState = {OFF:0, ON:1, BLINK:2}
var ledCurState = ledState.OFF;

// ROBOT
var robCurPos = "HOME"
var robCurState = "IDLE"

// LOG
let curLog = ""
/*
let curLog = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\
  Vivamus arcu felis bibendum ut tristique. Felis eget velit aliquet sagittis id consectetur purus. Suspendisse faucibus interdum posuere \
  lorem ipsum dolor sit amet consectetur. A diam sollicitudin tempor id eu nisl. Dolor morbi non arcu risus quis varius. Pellentesque habitant \
  morbi tristique senectus et netus et malesuada fames. Nunc mi ipsum faucibus vitae aliquet nec ullamcorper. Sed sed risus pretium quam vulputate \
  dignissim suspendisse in est. Non arcu risus quis varius quam quisque id. Sed egestas egestas fringilla phasellus. Pulvinar etiam non quam lacus. \
  Accumsan lacus vel facilisis volutpat est velit egestas. At tellus at urna condimentum mattis pellentesque id nibh. Mi tempus imperdiet nulla \
  malesuada pellentesque. Parturient montes nascetur ridiculus mus mauris vitae ultricies leo. Adipiscing diam donec adipiscing tristique risus \
  nec feugiat. Sit amet tellus cras adipiscing enim eu turpis egestas. Elit ullamcorper dignissim cras tincidunt. Egestas diam in arcu cursus \
  euismod quis viverra nibh cras. Nullam ac tortor vitae purus faucibus ornare suspendisse sed. Sit amet consectetur adipiscing elit duis. \
  Malesuada fames ac turpis egestas sed. Amet massa vitae tortor condimentum lacinia quis. Vestibulum rhoncus est pellentesque elit ullamcorper \
  dignissim cras tincidunt lobortis. Mi quis hendrerit dolor magna eget est lorem ipsum dolor. Morbi leo urna molestie at elementum eu facilisis. \
  Arcu dictum varius duis at consectetur lorem donec massa. Viverra tellus in hac habitasse platea dictumst vestibulum rhoncus est. Urna nunc id \
  cursus metus aliquam eleifend mi. Senectus et netus et malesuada fames ac turpis. Scelerisque eu ultrices vitae auctor eu augue ut. Porttitor \
  eget dolor morbi non. Bibendum enim facilisis gravida neque convallis a. Feugiat nibh sed pulvinar proin gravida hendrerit. Euismod nisi porta \
  lorem mollis aliquam ut. Mattis nunc sed blandit libero volutpat. Nec feugiat in fermentum posuere urna nec tincidunt praesent. Mollis aliquam \
  ut porttitor leo a diam sollicitudin. Tortor dignissim convallis aenean et tortor at. Neque gravida in fermentum et sollicitudin ac orci phasellus. \
  Ac orci phasellus egestas tellus rutrum tellus pellentesque eu. Odio ut sem nulla pharetra diam. Sit amet facilisis magna etiam tempor orci. \
  Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque. Sed risus pretium quam vulputate dignissim suspendisse in est ante. Et \
  egestas quis ipsum suspendisse ultrices gravida. Vulputate odio ut enim blandit volutpat maecenas volutpat. At imperdiet dui accumsan sit. Sed \
  cras ornare arcu dui vivamus. Sit amet consectetur adipiscing elit duis tristique sollicitudin nibh sit. Dignissim suspendisse in est ante in \
  nibh mauris cursus. Sed velit dignissim sodales ut eu sem integer. Viverra accumsan in nisl nisi scelerisque eu ultrices vitae auctor. Velit \
  scelerisque in dictum non consectetur a erat. Ut diam quam nulla porttitor massa id neque aliquam vestibulum. Non odio euismod lacinia at quis \
  risus. Quis hendrerit dolor magna eget est. Ipsum faucibus vitae aliquet nec ullamcorper sit. Integer malesuada nunc vel risus commodo viverra \
  maecenas. Neque ornare aenean euismod elementum. Sed augue lacus viverra vitae congue eu consequat. Viverra ipsum nunc aliquet bibendum enim \
  facilisis gravida. Fringilla est ullamcorper eget nulla facilisi. Non arcu risus quis varius quam quisque id diam. Pharetra convallis posuere \
  morbi leo. Erat velit scelerisque in dictum non consectetur a erat nam. Sagittis orci a scelerisque purus. Praesent semper feugiat nibh sed pulvinar."
  */

function init() {
  // HERE LOAD DATA FROM SERVER
  var gText = document.getElementById("glassText");
  gText.innerHTML = curGlassQt + "/" + maxGlassQt;

  var pText = document.getElementById("plasticText");
  pText.innerHTML = curPlastQt + "/" + maxPlastQt;

  var log = document.getElementById("log");
  log.innerHTML = curLog;

  var robotP = document.getElementById("robotPos");
  robotP.innerHTML = robCurPos;

  var robotS = document.getElementById("robotState");
  robotS.innerHTML = robCurState;
}

function updateMaxQt(newGlassQt, newPlastQt) {
  maxGlassQt = newGlassQt;
  maxPlastQt = newPlastQt;

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

        updateLog("glassQuantity(" + curGlassQt + "/" + maxGlassQt + ")")
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

        updateLog("plasticQuantity(" + curPlastQt + "/" + maxPlastQt + ")")
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

    updateLog("led(OFF)")
  }
}

function ledOn() {
  if(ledCurState !== ledState.ON){
    var led = document.getElementById("led");

    led.className = "led-on"
    ledCurState = ledState.ON

    updateLog("led(ON)")
  }
}

function ledBlink() {
  if(ledCurState !== ledState.BLINK){
    var led = document.getElementById("led");

    led.className = "led-blink"
    ledCurState = ledState.BLINK

    updateLog("led(BLINK)")
  }
}

function updatePosition(newPos) {
  if(robCurPos !== newPos){
    var pos = document.getElementById("robotPos");
    robCurPos = newPos.toUpperCase();

    pos.innerHTML = robCurPos;

    updateLog("robotPosition(" + robCurPos + ")")
  }
} 

function updateState(newState) {
  if(robCurState !== newState){
    var state = document.getElementById("robotState");
    robCurState = newState.toUpperCase();

    state.innerHTML = robCurState;

    updateLog("robotState(" + robCurState + ")")
  }
} 

function updateLog(newEntry) {
  var log = document.getElementById("log");

  curLog += ("</br>" + newEntry);

  log.innerHTML = curLog;
  log.scrollTop = log.scrollHeight;
}

function updPosTest() {
  var pos = document.getElementById("textArea");
  updatePosition(pos.value)
}