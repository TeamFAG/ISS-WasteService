%====================================================================================
% systemarchitecture description   
%====================================================================================
context(ctxdriver, "localhost",  "TCP", "8010").
context(ctxwasteservice, "localhost",  "TCP", "8011").
context(ctxgui, "localhost",  "TCP", "8012").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( wastetruck, ctxdriver, "it.unibo.wastetruck.Wastetruck").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( gui, ctxgui, "it.unibo.gui.Gui").
