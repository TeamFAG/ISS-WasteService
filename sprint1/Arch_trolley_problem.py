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
with Diagram('trolley_problemArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          mockwasteservice=Custom('mockwasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          trolleymover=Custom('trolleymover','./qakicons/symActorSmall.png')
          pathexecutor=Custom('pathexecutor','./qakicons/symActorSmall.png')
     mockwasteservice >> Edge(color='blue', style='solid', xlabel='simulate') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='move') >> trolleymover
     trolleymover >> Edge(color='magenta', style='solid', xlabel='stopPath') >> pathexecutor
     trolleymover >> Edge(color='magenta', style='solid', xlabel='doPath') >> pathexecutor
     pathexecutor >> Edge(color='blue', style='solid', xlabel='cmd') >> basicrobot
     pathexecutor >> Edge(color='magenta', style='solid', xlabel='step') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm') >> pathexecutor
diag
