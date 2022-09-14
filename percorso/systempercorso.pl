%====================================================================================
% systempercorso description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxpercorso, "localhost",  "TCP", "8030").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( pathexecutor, ctxpercorso, "it.unibo.pathexecutor.Pathexecutor").
  qactor( pathtester, ctxpercorso, "it.unibo.pathtester.Pathtester").
