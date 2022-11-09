%====================================================================================
% sprint2_project description   
%====================================================================================
context(ctxrasp, "localhost",  "TCP", "8070").
 qactor( sonaremittersimulator, ctxrasp, "wsSonar.sonarEmitterSimulator").
  qactor( sonaremitterconcrete, ctxrasp, "wsSonar.sonarEmitterConcrete").
  qactor( wsdatacleaner, ctxrasp, "wsSonar.dataCleaner").
  qactor( wsdistancefilter, ctxrasp, "wsSonar.distanceFilter").
  qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( sonar, ctxrasp, "it.unibo.sonar.Sonar").
  qactor( halteventshandler, ctxrasp, "it.unibo.halteventshandler.Halteventshandler").
