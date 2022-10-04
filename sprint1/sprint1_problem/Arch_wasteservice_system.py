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
with Diagram('wasteservice_systemArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservice_prob', graph_attr=nodeattr):
          wasteservice_t=Custom('wasteservice_t','./qakicons/symActorSmall.png')
          transporttrolley_t1=Custom('transporttrolley_t1','./qakicons/symActorSmall.png')
     wasteservice_t >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> transporttrolley_t1
     transporttrolley_t1 >> Edge(color='darkgreen', style='dashed', xlabel='pickupDone', fontcolor='darkgreen') >> wasteservice_t
diag
