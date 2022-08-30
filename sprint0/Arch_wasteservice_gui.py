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
with Diagram('wasteservice_guiArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservicegui', graph_attr=nodeattr):
          gui=Custom('gui','./qakicons/symActorSmall.png')
     sys >> Edge(color='red', style='dashed', xlabel='updatePosition') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateTrolleyStatus') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateLedStatus') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateWeight') >> gui
diag
