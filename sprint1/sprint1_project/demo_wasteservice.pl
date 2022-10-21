%====================================================================================
% demo_wasteservice description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8050").
context(ctxtrolley, "localhost",  "TCP", "8060").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxtrolley, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxtrolley, "it.unibo.trolleymover.Trolleymover").
  qactor( pather, ctxtrolley, "it.unibo.pather.Pather").
