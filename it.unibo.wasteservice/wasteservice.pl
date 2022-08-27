%====================================================================================
% wasteservice description   
%====================================================================================
context(ctxdeposit_wasteservice, "localhost",  "TCP", "8020").
 qactor( wasteservice, ctxdeposit_wasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxdeposit_wasteservice, "it.unibo.transporttrolley.Transporttrolley").
