/* Generated by AN DISI Unibo */ 
package it.unibo.wasteservice

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wasteservice ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
					var occupiedGlass : Double = 0.0
					var occupiedPlastic : Double = 0.0
					var currentMaterial : ws.Material
					var currentQuantity : Double
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition(edgeName="t10",targetState="handleRequest",cond=whenRequest("storeRequest"))
				}	 
				state("handleRequest") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("storeRequest(MATERIAL,QUANTITY)"), Term.createTerm("storeRequest(MATERIAL,QUANTITY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												currentMaterial = ws.Material.valueOf(payloadArg(0))
												currentQuantity = payloadArg(1).toDouble()
								println("Received request - $currentMaterial - $currentQuantity KG - OccupiedGlassKG: $occupiedGlass - OccupiedPlasticKG: $occupiedPlastic")
								if(  currentMaterial == ws.Material.GLASS && occupiedGlass + currentQuantity < ws.WasteServiceConstants.MAXGB || currentMaterial == ws.Material.PLASTIC && occupiedPlastic + currentQuantity < ws.WasteServiceConstants.MAXPB  
								 ){answer("storeRequest", "loadAccepted", "loadAccepted(_)"   )  
								}
								else
								 {answer("storeRequest", "loadRejected", "loadRejected(_)"   )  
								 }
						}
					}
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
			}
		}
}