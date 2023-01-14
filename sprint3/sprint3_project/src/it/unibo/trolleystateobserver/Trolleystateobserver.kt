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
				var CurrentTrolleyState = ""
				var CurrentTrolleyPosition = ""
				var LedState = ws.LedState.OFF
				var TrolleyState = ""
				var TrolleyPosition = ""
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("	TROLLEYSTATEOBSERVER | started.")
						CoapObserverSupport(myself, "localhost","8060","ctxtrolley","transporttrolley")
						CoapObserverSupport(myself, "localhost","8060","ctxtrolley","pather")
						updateResourceRep( "trolleystateobserver(IDLE)"  
						)
						updateResourceRep( "trolleystateobserver(HOME)"  
						)
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
													LedState = ws.ObserversUtils.getLedStatusFromCoapUpdate(Resource, Value)
													
												if(Resource.equals("transporttrolley") && Value.contains("arrived"))
													TrolleyPosition = ws.ObserversUtils.getTrolleyPositionFromCoapUpdate(Resource, Value)
													
												TrolleyState = ws.ObserversUtils.getTrolleyStateFromCoapUpdate(Resource, Value)
								if(  !CurrentTrolleyState.equals(TrolleyState)  
								 ){updateResourceRep( "trolleystateobserver($TrolleyState)"  
								)
								 CurrentTrolleyState = TrolleyState  
								}
								if(  !CurrentState.equals(LedState)  
								 ){forward("updateLed", "updateLed($LedState)" ,"led" ) 
								 CurrentState = LedState  
								}
								if(  !CurrentTrolleyPosition.equals(TrolleyPosition)  
								 ){updateResourceRep( "trolleystateobserver(arrived_${TrolleyPosition})"  
								)
								 CurrentTrolleyPosition = TrolleyPosition  
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
