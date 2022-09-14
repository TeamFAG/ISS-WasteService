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
with Diagram('systempercorsoArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxpercorso', graph_attr=nodeattr):
          pathexecutor=Custom('pathexecutor','./qakicons/symActorSmall.png')
          pathtester=Custom('pathtester','./qakicons/symActorSmall.png')
     pathexecutor >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     pathexecutor >> Edge(color='magenta', style='solid', xlabel='step', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> pathexecutor
     pathtester >> Edge(color='magenta', style='solid', xlabel='dopath', fontcolor='magenta') >> pathexecutor
     pathtester >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
diag
