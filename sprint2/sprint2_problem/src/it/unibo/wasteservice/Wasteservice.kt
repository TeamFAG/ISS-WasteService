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
		
				var CurrentMaterial: ws.Material
				var CurrentQuantity: Float
				var Rejected: Boolean = false
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("	WASTESERVICE | started.")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t01",targetState="handleStoreRequest",cond=whenRequest("storeRequest"))
				}	 
				state("idle") { //this:State
					action { //it:State
						 Rejected = false  
						println("	WASTESERVICE | idle - waiting for storeRequests")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t02",targetState="handleStoreRequest",cond=whenRequest("storeRequest"))
				}	 
				state("handleStoreRequest") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("storeRequest(MATERIAL,QUANTITY)"), Term.createTerm("storeRequest(MATERIAL,QUANTITY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												CurrentMaterial = ws.Material.valueOf(payloadArg(0))
												CurrentQuantity = payloadArg(1).toFloat()
								updateResourceRep( "wasteservice(handleStoreRequest_${CurrentMaterial.toString()}_$CurrentQuantity)"  
								)
								println("	WASTESERVICE | received store request: $CurrentQuantity KG of $CurrentMaterial")
								if(  ws.WasteServiceStatusManager.checkIfDepositPossible(CurrentMaterial, CurrentQuantity)  
								 ){
													ws.WasteServiceStatusManager.updateBox(CurrentMaterial, CurrentQuantity)
								updateResourceRep( "wasteservice(Plastic: ${ws.WasteServiceStatusManager.storedPlastic})"  
								)
								updateResourceRep( "wasteservice(Glass: ${ws.WasteServiceStatusManager.storedGlass})"  
								)
								request("depositRequest", "depositRequest($CurrentMaterial,$CurrentQuantity)" ,"transporttrolley" )  
								println("	WASTESERVICE | sended depositRequest to trolley")
								}
								else
								 { Rejected = true  
								 updateResourceRep( "wasteservice(handleStoreRequest_loadRejected)"  
								 )
								 answer("storeRequest", "loadRejected", "loadRejected(_)"   )  
								 println("	WASTESERVICE | rejected request from smartdevice")
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitchGuarded({ Rejected  
					}) )
					transition( edgeName="goto",targetState="pickupWait", cond=doswitchGuarded({! ( Rejected  
					) }) )
				}	 
				state("pickupWait") { //this:State
					action { //it:State
						println("	WASTSERVICE | pickupWait")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t13",targetState="handlePickupReply",cond=whenReply("pickupDone"))
				}	 
				state("handlePickupReply") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("pickupDone(RESULT)"), Term.createTerm("pickupDone(RESULT)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var Res = payloadArg(0).toString()  
								if(  Res == "OK"  
								 ){updateResourceRep( "wasteservice(handlePickupReply_loadAccepted)"  
								)
								println("	WASTESERVICE | arrived pickupDone reply OK")
								answer("storeRequest", "loadAccepted", "loadAccepted(_)"   )  
								}
								else
								 {updateResourceRep( "wasteservice(handlePickupReply_loadRejected)"  
								 )
								 println("	WASTESERVICE | arrived pickupDone reply NO - FATAL ERROR")
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
