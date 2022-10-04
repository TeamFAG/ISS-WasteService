%====================================================================================
% wasteservice_system description   
%====================================================================================
context(ctxwasteservice_prob, "localhost",  "TCP", "8040").
 qactor( wasteservice_t, ctxwasteservice_prob, "it.unibo.wasteservice_t.Wasteservice_t").
  qactor( transporttrolley_t1, ctxwasteservice_prob, "it.unibo.transporttrolley_t1.Transporttrolley_t1").
