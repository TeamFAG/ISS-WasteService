%====================================================================================
% old description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxwasteservice, "127.0.0.1",  "TCP", "8030").
context(ctxsmartdevice, "localhost",  "TCP", "8040").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( smartdevicemock, ctxsmartdevice, "it.unibo.smartdevicemock.Smartdevicemock").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxbasicrobot, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxbasicrobot, "it.unibo.trolleymover.Trolleymover").
  qactor( ourpathexecutor, ctxbasicrobot, "it.unibo.ourpathexecutor.Ourpathexecutor").
