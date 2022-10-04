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
with Diagram('trolleymover_systemArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          trolleymover=Custom('trolleymover','./qakicons/symActorSmall.png')
          ourpathexecutor=Custom('ourpathexecutor','./qakicons/symActorSmall.png')
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     trolleymover >> Edge(color='magenta', style='solid', xlabel='stopPath', fontcolor='magenta') >> ourpathexecutor
     trolleymover >> Edge(color='magenta', style='solid', xlabel='doPath', fontcolor='magenta') >> ourpathexecutor
     ourpathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='stopACK', fontcolor='darkgreen') >> trolleymover
     ourpathexecutor >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     ourpathexecutor >> Edge(color='magenta', style='solid', xlabel='step', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> ourpathexecutor
     ourpathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='doPathDone', fontcolor='darkgreen') >> trolleymover
     ourpathexecutor >> Edge(color='darkgreen', style='dashed', xlabel='doPathFail', fontcolor='darkgreen') >> trolleymover
diag
