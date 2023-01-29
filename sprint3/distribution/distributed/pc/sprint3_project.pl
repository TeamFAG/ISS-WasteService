%====================================================================================
% sprint3_project description   
%====================================================================================
context(ctxwasteservice, "localhost",  "TCP", "8050").
context(ctxtrolley, "localhost",  "TCP", "8060").
context(ctxrasp, "192.168.1.122",  "TCP", "8070").
  qactor( sonaremittersimulator, ctxrasp, "wsSonar.sonarEmitterSimulator").
  qactor( sonaremitterconcrete, ctxrasp, "wsSonar.sonarEmitterConcrete").
  qactor( wsdatacleaner, ctxrasp, "wsSonar.dataCleaner").
  qactor( wsdistancefilter, ctxrasp, "wsSonar.distanceFilter").
  qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxtrolley, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxtrolley, "it.unibo.trolleymover.Trolleymover").
  qactor( pather, ctxtrolley, "it.unibo.pather.Pather").
  qactor( trolleystateobserver, ctxtrolley, "it.unibo.trolleystateobserver.Trolleystateobserver").
  qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( sonar, ctxrasp, "it.unibo.sonar.Sonar").
  qactor( halteventshandler, ctxrasp, "it.unibo.halteventshandler.Halteventshandler").
  qactor( datacleaner, ctxtrolley, "rx.dataCleaner").
  qactor( distancefilter, ctxtrolley, "rx.distanceFilter").
  qactor( basicrobot, ctxtrolley, "it.unibo.basicrobot.Basicrobot").
  qactor( envsonarhandler, ctxtrolley, "it.unibo.envsonarhandler.Envsonarhandler").
