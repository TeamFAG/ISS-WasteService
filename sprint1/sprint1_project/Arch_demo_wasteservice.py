from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'D:/Programmi/Graphviz/bin/'

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
with Diagram('demo_wasteserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
     with Cluster('ctxtrolley', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          trolleymover=Custom('trolleymover','./qakicons/symActorSmall.png')
          pather=Custom('pather','./qakicons/symActorSmall.png')
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
diag
