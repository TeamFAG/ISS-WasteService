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
with Diagram('core_problem_analysisArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     with Cluster('ctxsmartdevice', graph_attr=nodeattr):
          smartdevicemock=Custom('smartdevicemock','./qakicons/symActorSmall.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     wasteservice >> Edge(color='magenta', style='solid', xlabel='depositRequest', fontcolor='magenta') >> transporttrolley
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadRejected', fontcolor='darkgreen') >> smartdevicemock
     wasteservice >> Edge(color='darkgreen', style='dashed', xlabel='loadAccepted', fontcolor='darkgreen') >> smartdevicemock
     transporttrolley >> Edge(color='darkgreen', style='dashed', xlabel='pickupDone', fontcolor='darkgreen') >> wasteservice
     smartdevicemock >> Edge(color='magenta', style='solid', xlabel='storeRequest', fontcolor='magenta') >> wasteservice
diag
