%====================================================================================
% sprint2_project_single_context description   
%====================================================================================
context(ctxwasteservice_test, "localhost",  "TCP", "8050").
 qactor( sonaremittersimulator, ctxwasteservice_test, "wsSonar.sonarEmitterSimulator").
  qactor( wsdatacleaner, ctxwasteservice_test, "wsSonar.dataCleaner").
  qactor( wsdistancefilter, ctxwasteservice_test, "wsSonar.distanceFilter").
  qactor( wasteservice, ctxwasteservice_test, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice_test, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxwasteservice_test, "it.unibo.trolleymover.Trolleymover").
  qactor( pather, ctxwasteservice_test, "it.unibo.pather.Pather").
  qactor( trolleystateobserver, ctxwasteservice_test, "it.unibo.trolleystateobserver.Trolleystateobserver").
  qactor( ledcontroller, ctxwasteservice_test, "it.unibo.ledcontroller.Ledcontroller").
  qactor( sonar, ctxwasteservice_test, "it.unibo.sonar.Sonar").
  qactor( halteventshandler, ctxwasteservice_test, "it.unibo.halteventshandler.Halteventshandler").
  qactor( datacleaner, ctxwasteservice_test, "rx.dataCleaner").
  qactor( distancefilter, ctxwasteservice_test, "rx.distanceFilter").
  qactor( basicrobot, ctxwasteservice_test, "it.unibo.basicrobot.Basicrobot").
  qactor( envsonarhandler, ctxwasteservice_test, "it.unibo.envsonarhandler.Envsonarhandler").
