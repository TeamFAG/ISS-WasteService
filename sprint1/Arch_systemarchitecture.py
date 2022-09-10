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
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadAccepted', fontcolor='darkgreen') >> wastetruck
     wasteservice >> Edge(color='blue', style='solid', xlabel='notifyDeposit', fontcolor='blue') >> transporttrolley
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadRejected', fontcolor='darkgreen') >> wastetruck
     wastetruck >> Edge(color='magenta', style='solid', xlabel='storeRequest', fontcolor='magenta') >> wasteservice
     transporttrolley >> Edge( xlabel='updatePosition', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( xlabel='updateTrolleyStatus', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='blue', style='solid', xlabel='cmd', fontcolor='blue') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='step', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge( xlabel='updateWeight', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='updatePosition', fontcolor='red') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateTrolleyStatus', fontcolor='red') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateLedStatus', fontcolor='red') >> gui
     sys >> Edge(color='red', style='dashed', xlabel='updateWeight', fontcolor='red') >> gui
diag
