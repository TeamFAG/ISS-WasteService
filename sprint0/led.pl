%====================================================================================
% led description   
%====================================================================================
context(ctxrasp, "localhost",  "TCP", "8013").
 qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( sonar, ctxrasp, "it.unibo.sonar.Sonar").
