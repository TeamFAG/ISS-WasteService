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
with Diagram('systemarchitectureArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxdriver', graph_attr=nodeattr):
          wastetruck=Custom('wastetruck','./qakicons/symActorSmall.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     with Cluster('ctxgui', graph_attr=nodeattr):
          gui=Custom('gui','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     wasteservice >> Edge(color='blue', style='solid', xlabel='notifyDeposit') >> transporttrolley
     wastetruck >> Edge(color='magenta', style='solid', xlabel='storeRequest') >> wasteservice
     transporttrolley >> Edge( xlabel='updatePosition', **eventedgeattr) >> sys
     transporttrolley >> Edge( xlabel='updateTrolleyStatus', **eventedgeattr) >> sys
     transporttrolley >> Edge(color='blue', style='solid', xlabel='cmd') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='step') >> basicrobot
     transporttrolley >> Edge( xlabel='updateWeight', **eventedgeattr) >> sys
     sys >> Edge(color='red', style='dashed', xlabel='updatePosition') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateTrolleyStatus') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateLedStatus') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateWeight') >> gui
diag
