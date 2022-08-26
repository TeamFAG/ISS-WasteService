%====================================================================================
% wasteservice description   
%====================================================================================
context(ctxrequest_wasteservice, "localhost",  "TCP", "8010").
 qactor( wasteservice, ctxrequest_wasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( wastetruck, ctxrequest_wasteservice, "it.unibo.wastetruck.Wastetruck").
