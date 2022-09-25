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
with Diagram('trolley_problemArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          trolleymover=Custom('trolleymover','./qakicons/symActorSmall.png')
          pathexecutor=Custom('pathexecutor','./qakicons/symActorSmall.png')
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move', fontcolor='magenta') >> trolleymover
     trolleymover >> Edge(color='magenta', style='solid', xlabel='stopPath', fontcolor='magenta') >> pathexecutor
     trolleymover >> Edge(color='magenta', style='solid', xlabel='doPath', fontcolor='magenta') >> pathexecutor
     pathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='stopACK', fontcolor='darkgreen') >> trolleymover
     pathexecutor >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     pathexecutor >> Edge(color='magenta', style='solid', xlabel='step', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> pathexecutor
     pathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='doPathDone', fontcolor='darkgreen') >> trolleymover
     pathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='doPathFail', fontcolor='darkgreen') >> trolleymover
diag
