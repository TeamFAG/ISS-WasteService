%====================================================================================
% sprint2_project description   
%====================================================================================
context(ctxrasp, "rasp",  "TCP", "8070").
context(ctxtrolley, "192.168.50.94",  "TCP", "8060").
 % qactor( sonaremittersimulator, ctxrasp, "wsSonar.sonarEmitterSimulator").
  qactor( sonaremitterconcrete, ctxrasp, "wsSonar.sonarEmitterConcrete").
  qactor( wsdatacleaner, ctxrasp, "wsSonar.dataCleaner").
  qactor( wsdistancefilter, ctxrasp, "wsSonar.distanceFilter").
  qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( sonar, ctxrasp, "it.unibo.sonar.Sonar").
  qactor( halteventshandler, ctxrasp, "it.unibo.halteventshandler.Halteventshandler").
  qactor( pather, ctxtrolley, "it.unibo.pather.Pather").
