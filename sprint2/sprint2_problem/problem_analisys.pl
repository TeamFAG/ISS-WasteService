%====================================================================================
% problem_analisys description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
context(ctxwasteservice, "localhost",  "TCP", "8050").
context(ctxtrolley, "localhost",  "TCP", "8060").
context(ctxrasp, "localhost",  "TCP", "8070").
 qactor( wasteservice, ctxwasteservice, "it.unibo.wasteservice.Wasteservice").
  qactor( transporttrolley, ctxtrolley, "it.unibo.transporttrolley.Transporttrolley").
  qactor( trolleymover, ctxtrolley, "it.unibo.trolleymover.Trolleymover").
  qactor( pather, ctxtrolley, "it.unibo.pather.Pather").
  qactor( trolleystateobserver, ctxrasp, "it.unibo.trolleystateobserver.Trolleystateobserver").
  qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( sonarmockemitter, ctxrasp, "it.unibo.sonarmockemitter.Sonarmockemitter").
  qactor( sonarfilter, ctxrasp, "it.unibo.sonarfilter.Sonarfilter").
  qactor( halteventshandler, ctxrasp, "it.unibo.halteventshandler.Halteventshandler").
  qactor( datacleaner, ctxbasicrobot, "rx.dataCleaner").
  qactor( distancefilter, ctxbasicrobot, "rx.distanceFilter").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
