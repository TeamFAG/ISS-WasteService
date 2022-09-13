/* Generated by AN DISI Unibo */ 
package it.unibo.gui

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Gui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				var POSITION = ws.TrolleyPosition.HOME
				var TROLLEY = ws.TrolleyStatus.IDLE
				var LED = ws.LedState.OFF
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t010",targetState="handlePosition",cond=whenEvent("updatePosition"))
					transition(edgeName="t011",targetState="handleTrolleyStatus",cond=whenEvent("updateTrolleyStatus"))
					transition(edgeName="t012",targetState="handleLedStatus",cond=whenEvent("updateLedStatus"))
					transition(edgeName="t013",targetState="handleWeight",cond=whenEvent("updateWeight"))
				}	 
				state("handlePosition") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("updatePosition(POSITION)"), Term.createTerm("updatePosition(POSITION)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												POSITION = ws.TrolleyPosition.valueOf(payloadArg(0))	
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("handleTrolleyStatus") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("updateTrolleyStatus(STATUS)"), Term.createTerm("updateTrolleyStatus(STATUS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												TROLLEY = ws.TrolleyStatus.valueOf(payloadArg(0))	
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("handleLedStatus") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("updateLedStatus(STATUS)"), Term.createTerm("updateLedStatus(STATUS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												LED = ws.LedState.valueOf(payloadArg(0))	
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("handleWeight") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("updateWeight(_)"), Term.createTerm("updateWeight(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
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
