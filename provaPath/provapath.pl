%====================================================================================
% provapath description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxpath, "localhost",  "TCP", "8030").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( tester, ctxpath, "it.unibo.tester.Tester").
  qactor( pathexec, ctxpath, "it.unibo.pathexec.Pathexec").
