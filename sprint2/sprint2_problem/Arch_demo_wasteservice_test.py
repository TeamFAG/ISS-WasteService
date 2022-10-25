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
with Diagram('demo_wasteservice_testArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservice_test', graph_attr=nodeattr):
          sonarinterfacemock=Custom('sonarinterfacemock','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          trolleymover=Custom('trolleymover','./qakicons/symActorSmall.png')
          pather=Custom('pather','./qakicons/symActorSmall.png')
          halteventshandler=Custom('halteventshandler','./qakicons/symActorSmall.png')
          basicrobot=Custom('basicrobot','./qakicons/symActorSmall.png')
          envsonarhandler=Custom('envsonarhandler','./qakicons/symActorSmall.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
     sonarinterfacemock >> Edge( xlabel='startHalt', **eventedgeattr, fontcolor='red') >> sys
     sonarinterfacemock >> Edge( xlabel='stopHalt', **eventedgeattr, fontcolor='red') >> sys
     wasteservice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move', fontcolor='magenta') >> trolleymover
     trolleymover >> Edge(color='magenta', style='solid', xlabel='stopPath', fontcolor='magenta') >> pather
     trolleymover >> Edge(color='magenta', style='solid', xlabel='doPath', fontcolor='magenta') >> pather
     pather >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     pather >> Edge(color='magenta', style='solid', xlabel='step', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> pather
     sys >> Edge(color='red', style='dashed', xlabel='startHalt', fontcolor='red') >> halteventshandler
     halteventshandler >> Edge(color='blue', style='solid', xlabel='halt', fontcolor='blue') >> pather
     sys >> Edge(color='red', style='dashed', xlabel='stopHalt', fontcolor='red') >> halteventshandler
     halteventshandler >> Edge(color='blue', style='solid', xlabel='resume', fontcolor='blue') >> pather
     sys >> Edge(color='red', style='dashed', xlabel='sonar', fontcolor='red') >> envsonarhandler
diag
