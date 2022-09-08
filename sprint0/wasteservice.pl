%====================================================================================
% wasteservice description   
%====================================================================================
context(ctxrequest, "localhost",  "TCP", "8010").
 qactor( wasteservice, ctxrequest, "it.unibo.wasteservice.Wasteservice").
  qactor( wastetruck, ctxrequest, "it.unibo.wastetruck.Wastetruck").
