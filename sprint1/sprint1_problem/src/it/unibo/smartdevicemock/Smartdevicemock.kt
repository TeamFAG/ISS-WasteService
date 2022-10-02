/* Generated by AN DISI Unibo */ 
package it.unibo.smartdevicemock

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Smartdevicemock ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

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
									Qty = kotlin.random.Random.nextDouble(10.0, 50.0)
						println("	WASTETRUCK | with $Qty KG of $Material")
						request("storeRequest", "storeRequest($Material,$Qty)" ,"wasteservice" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t15",targetState="handleAccepted",cond=whenReply("loadAccepted"))
					transition(edgeName="t16",targetState="handleRejected",cond=whenReply("loadRejected"))
				}	 
				state("handleAccepted") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("	WASTETRUCK: Store Accepted")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="termination", cond=doswitch() )
				}	 
				state("handleRejected") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("	WASTETRUCK: Store Rejected")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="termination", cond=doswitch() )
				}	 
				state("termination") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("	WASTETRUCK: going away...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
			}
		}
}
