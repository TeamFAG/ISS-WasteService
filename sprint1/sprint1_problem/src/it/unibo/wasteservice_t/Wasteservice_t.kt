/* Generated by AN DISI Unibo */ 
package it.unibo.wasteservice_t

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wasteservice_t ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var occupiedGlass : Double = 0.0
				var occupiedPlastic : Double = 0.0
				var CurrentMaterial : ws.Material
				var CurrentQuantity : Float
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t10",targetState="handleStoreRequest",cond=whenRequest("storeRequest"))
				}	 
				state("idle") { //this:State
					action { //it:State
						println("	WASTESERVICE | idle - waiting for store requests")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t21",targetState="handleStoreRequest",cond=whenRequest("storeRequest"))
				}	 
				state("handleStoreRequest") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("storeRequest(MATERIAL,QUANTITY)"), Term.createTerm("storeRequest(MATERIAL,QUANTITY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												CurrentMaterial = ws.Material.valueOf(payloadArg(0))
												CurrentQuantity = payloadArg(1).toFloat()
								println("	WASTESERVICE | received store request: $CurrentQuantity KG of $CurrentMaterial")
								if(  ws.WasteServiceStatusManager.checkIfDepositPossible(CurrentMaterial, CurrentQuantity)  
								 ){ ws.WasteServiceStatusManager.updateBox(CurrentMaterial, CurrentQuantity)  
								println("	WASTESERVICE | accepted request from truck driver")
								request("depositRequest", "depositRequest($CurrentMaterial,$CurrentQuantity)" ,"transporttrolley_t1" )  
								println("	WASTESERVICE | notifying transporttrolley")
								}
								else
								 {answer("storeRequest", "loadRejected", "loadRejected(_)"   )  
								 println("	WASTESERVICE | rejected request from truck driver")
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t22",targetState="handlePickupReply",cond=whenReply("pickupDone"))
				}	 
				state("handlePickupReply") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("pickupDone(RESULT)"), Term.createTerm("pickupDone(RESULT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Res = payloadArg(0)  
								if(  Res == "ok"  
								 ){println("	WASTESERVICE | arrived pickupDone reply ok")
								answer("storeRequest", "loadAccepted", "loadAccepted(_)"   )  
								}
								else
								 {println("	WASTESERVICE | arrived pickupDone reply ko - FATAL ERROR")
								 answer("storeRequest", "loadRejected", "loadRejected(_)"   )  
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
			}
		}
}
