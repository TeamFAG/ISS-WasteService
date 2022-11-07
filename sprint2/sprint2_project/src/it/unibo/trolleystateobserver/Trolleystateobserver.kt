/* Generated by AN DISI Unibo */ 
package it.unibo.trolleystateobserver

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Trolleystateobserver ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var CurrentState = ws.LedState.OFF
				var LedState = ws.LedState.OFF
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("	TROLLEYSTATEOBSERVER | started.")
						CoapObserverSupport(myself, "192.168.1.125","8060","ctxtrolley","transporttrolley")
						CoapObserverSupport(myself, "192.168.1.125","8060","ctxtrolley","pather")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t026",targetState="handleCoapUpdate",cond=whenDispatch("coapUpdate"))
				}	 
				state("handleCoapUpdate") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("coapUpdate(RESOURCE,VALUE)"), Term.createTerm("coapUpdate(RESOURCE,VALUE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												var Resource = payloadArg(0)
												var Value = payloadArg(1)
												
												if(!Value.contains("pickupDone") && !Value.contains("handleDepositRequest")) 
													LedState = wsLed.LedUtils.getLedStatusFromCoap(Resource, Value)
								if(  !CurrentState.equals(LedState)  
								 ){emit("updateLed", "updateLed($LedState)" ) 
								 CurrentState = LedState  
								}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t027",targetState="handleCoapUpdate",cond=whenDispatch("coapUpdate"))
				}	 
			}
		}
}
