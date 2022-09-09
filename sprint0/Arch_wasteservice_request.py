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
with Diagram('wasteservice_requestArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxrequest', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          wastetruck=Custom('wastetruck','./qakicons/symActorSmall.png')
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadAccepted', fontcolor='darkgreen') >> wastetruck
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadRejected', fontcolor='darkgreen') >> wastetruck
     wastetruck >> Edge(color='magenta', style='solid', xlabel='storeRequest', fontcolor='magenta') >> wasteservice
diag
