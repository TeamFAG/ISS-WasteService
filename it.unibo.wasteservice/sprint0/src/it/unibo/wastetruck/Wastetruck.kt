/* Generated by AN DISI Unibo */ 
package it.unibo.wastetruck

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wastetruck ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var Material : ws.Material
				var Qty : Double
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						
									Material = if(kotlin.random.Random.nextBoolean()) ws.Material.GLASS else ws.Material.PLASTIC
									Qty = kotlin.random.Random.nextDouble(100.0, 120.0)
						println("WasteTruck with $Qty KG of $Material")
						request("storeRequest", "storeRequest($Material,$Qty)" ,"wasteservice" )  
					}
					 transition(edgeName="t11",targetState="handleAccepted",cond=whenReply("loadAccepted"))
					transition(edgeName="t12",targetState="handleRejected",cond=whenReply("loadRejected"))
				}	 
				state("handleAccepted") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("Store Accepted")
					}
					 transition( edgeName="goto",targetState="termination", cond=doswitch() )
				}	 
				state("handleRejected") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("Store Rejected")
					}
					 transition( edgeName="goto",targetState="termination", cond=doswitch() )
				}	 
				state("termination") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("Termination")
					}
				}	 
			}
		}
}
