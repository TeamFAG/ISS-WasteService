%====================================================================================
% demo_wasteservice_test description   
%====================================================================================
context(ctxwasteservice_test, "localhost",  "TCP", "8050").
 qactor( wasteservice, ctxwasteservice_test, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxwasteservice_test, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxwasteservice_test, "it.unibo.trolleymover.Trolleymover").
  qactor( pather, ctxwasteservice_test, "it.unibo.pather.Pather").
  qactor( trolleystateobserver, ctxwasteservice_test, "it.unibo.trolleystateobserver.Trolleystateobserver").
  qactor( led, ctxwasteservice_test, "it.unibo.led.Led").
  qactor( sonarfilter, ctxwasteservice_test, "it.unibo.sonarfilter.Sonarfilter").
  qactor( halteventshandler, ctxwasteservice_test, "it.unibo.halteventshandler.Halteventshandler").
  qactor( basicrobot, ctxwasteservice_test, "it.unibo.basicrobot.Basicrobot").
