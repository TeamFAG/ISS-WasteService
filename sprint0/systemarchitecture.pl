%====================================================================================
% systemarchitecture description   
%====================================================================================
context(ctxdriver, "localhost",  "TCP", "8010").
context(ctxwasteservice, "localhost",  "TCP", "8011").
context(ctxgui, "localhost",  "TCP", "8012").
context(ctxrasp, "localhost",  "TCP", "8013").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( wastetruck, ctxdriver, "it.unibo.wastetruck.Wastetruck").
  qactor( gui, ctxgui, "it.unibo.gui.Gui").
  qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( sonar, ctxrasp, "it.unibo.sonar.Sonar").
