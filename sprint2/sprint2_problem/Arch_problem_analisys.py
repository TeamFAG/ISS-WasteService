from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('problem_analisysArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot','./qakicons/symActorSmall.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
     with Cluster('ctxtrolley', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          trolleymover=Custom('trolleymover','./qakicons/symActorSmall.png')
          pather=Custom('pather','./qakicons/symActorSmall.png')
     with Cluster('ctxrasp', graph_attr=nodeattr):
          trolleystateobserver=Custom('trolleystateobserver','./qakicons/symActorSmall.png')
          led=Custom('led','./qakicons/symActorSmall.png')
          sonarmockemitter=Custom('sonarmockemitter','./qakicons/symActorSmall.png')
          sonarfilter=Custom('sonarfilter','./qakicons/symActorSmall.png')
          halteventshandler=Custom('halteventshandler','./qakicons/symActorSmall.png')
     wasteservice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move', fontcolor='magenta') >> trolleymover
     transporttrolley >> Edge(color='darkgreen', style='dashed', xlabel='pickupDone', fontcolor='darkgreen') >> wasteservice
     trolleymover >> Edge(color='magenta', style='solid', xlabel='stopPath', fontcolor='magenta') >> pather
     trolleymover >> Edge(color='magenta', style='solid', xlabel='doPath', fontcolor='magenta') >> pather
     trolleymover >> Edge(color='darkgreen', style='dashed', xlabel='moveDone', fontcolor='darkgreen') >> transporttrolley
     pather >> Edge(color='darkgreen', style='dashed', xlabel='stopAck', fontcolor='darkgreen') >> trolleymover
     pather >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     pather >> Edge(color='magenta', style='solid', xlabel='step', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> pather
     pather >> Edge(color='darkgreen', style='dashed', xlabel='doPathDone', fontcolor='darkgreen') >> trolleymover
     pather >> Edge(color='darkgreen', style='dashed', xlabel='doPathFail', fontcolor='darkgreen') >> trolleymover
     transporttrolley >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> trolleystateobserver
     pather >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> trolleystateobserver
     trolleystateobserver >> Edge( xlabel='updateLed', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='updateLed', fontcolor='red') >> led
     sonarmockemitter >> Edge( xlabel='distance', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='distance', fontcolor='red') >> sonarfilter
     sonarfilter >> Edge( xlabel='startHalt', **eventedgeattr, fontcolor='red') >> sys
     sonarfilter >> Edge( xlabel='stopHalt', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='startHalt', fontcolor='red') >> halteventshandler
     halteventshandler >> Edge(color='blue', style='solid', xlabel='halt', fontcolor='blue') >> pather
     sys >> Edge(color='red', style='dashed', xlabel='stopHalt', fontcolor='red') >> halteventshandler
     halteventshandler >> Edge(color='blue', style='solid', xlabel='resume', fontcolor='blue') >> pather
     basicrobot >> Edge(color='darkgreen', style='dashed', xlabel='stepdone', fontcolor='darkgreen') >> pather
     basicrobot >> Edge(color='darkgreen', style='dashed', xlabel='stepfail', fontcolor='darkgreen') >> pather
diag
