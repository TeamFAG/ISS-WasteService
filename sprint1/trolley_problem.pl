%====================================================================================
% trolley_problem description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8030").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( mockwasteservice, ctxwasteservice, "it.unibo.mockwasteservice.Mockwasteservice").
  qactor( transporttrolley, ctxwasteservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxwasteservice, "it.unibo.trolleymover.Trolleymover").
  qactor( pathexecutor, ctxwasteservice, "it.unibo.pathexecutor.Pathexecutor").
