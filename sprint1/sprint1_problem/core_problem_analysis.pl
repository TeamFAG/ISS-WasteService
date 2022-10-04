%====================================================================================
% core_problem_analysis description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8010").
context(ctxsmartdevice, "localhost",  "TCP", "8011").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( smartdevicemock, ctxsmartdevice, "it.unibo.smartdevicemock.Smartdevicemock").
