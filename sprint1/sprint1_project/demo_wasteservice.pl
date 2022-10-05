%====================================================================================
% demo_wasteservice description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxsmartdevice, "127.0.0.1",  "TCP", "8040").
context(ctxwasteservice, "localhost",  "TCP", "8050").
context(ctxtrolley, "localhost",  "TCP", "8060").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( smartdevicemock, ctxsmartdevice, "it.unibo.smartdevicemock.Smartdevicemock").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxtrolley, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxtrolley, "it.unibo.trolleymover.Trolleymover").
  qactor( ourpathexecutor, ctxtrolley, "it.unibo.ourpathexecutor.Ourpathexecutor").
