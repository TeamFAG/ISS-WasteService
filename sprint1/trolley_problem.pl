%====================================================================================
% trolley_problem description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxwasteservice, "127.0.0.1",  "TCP", "8038").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxwasteservice, "it.unibo.trolleymover.Trolleymover").
  qactor( pathexecutor, ctxwasteservice, "it.unibo.pathexecutor.Pathexecutor").
