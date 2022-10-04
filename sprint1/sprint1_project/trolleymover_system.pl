%====================================================================================
% trolleymover_system description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( trolleymover, ctxbasicrobot, "it.unibo.trolleymover.Trolleymover").
  qactor( ourpathexecutor, ctxbasicrobot, "it.unibo.ourpathexecutor.Ourpathexecutor").
