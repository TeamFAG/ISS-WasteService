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
		
					var StoredPlastic = 0.0
					var StoredGlass = 0.0
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("	WasteService | current plastic: $StoredPlastic, current glass: $StoredGlass")
						delay(1000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="sendDeposit", cond=doswitch() )
				}	 
				state("sendDeposit") { //this:State
					action { //it:State
						
									var MaterialType = if(kotlin.random.Random.nextBoolean()) ws.Material.GLASS else ws.Material.PLASTIC
									var Quantity = kotlin.random.Random.nextDouble(10.0, 50.0)
						forward("notifyDeposit", "notifyDeposit($MaterialType,$Quantity)" ,"transporttrolley" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("	WasteService | current plastic: $StoredPlastic, current glass: $StoredGlass")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t00",targetState="updateValues",cond=whenDispatch("updateWasteService"))
				}	 
				state("updateValues") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("updateWasteService(MATERIAL,QUANTITY)"), Term.createTerm("updateWasteService(MATERIAL,QUANTITY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								if(  ws.Material.valueOf(payloadArg(0)) == ws.Material.PLASTIC  
								 ){ StoredPlastic += payloadArg(1).toDouble()  
								}
								else
								 { StoredGlass += payloadArg(1).toDouble()  
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
			}
		}
}
