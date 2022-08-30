%====================================================================================
% wasteservice_deposit description   
%====================================================================================
context(ctxdeposit, "localhost",  "TCP", "8020").
 qactor( wasteservice, ctxdeposit, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxdeposit, "it.unibo.transporttrolley.Transporttrolley").
