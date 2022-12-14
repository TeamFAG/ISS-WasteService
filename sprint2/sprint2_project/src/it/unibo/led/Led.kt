/* Generated by AN DISI Unibo */ 
package it.unibo.led

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Led ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var LedState = ws.LedState.OFF
				var Simulation: Boolean = true
				lateinit var Led: `it.unibo`.radarSystem22.domain.interfaces.ILed
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("	LED | started.")
						updateResourceRep( "led(OFF)"  
						)
						
									SystemConfig.setTheConfiguration("SystemConfiguration")
									Simulation = SystemConfig.sonar["simulation"] as Boolean
									
									wsLed.LedUtils.createLed(Simulation, Simulation)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t028",targetState="handleEvent",cond=whenDispatch("updateLed"))
					transition(edgeName="t029",targetState="turnOnMsg",cond=whenDispatch("turnOnLed"))
					transition(edgeName="t030",targetState="turnOffMsg",cond=whenDispatch("turnOffLed"))
				}	 
				state("idle") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t031",targetState="handleEvent",cond=whenDispatch("updateLed"))
					transition(edgeName="t032",targetState="turnOnMsg",cond=whenDispatch("turnOnLed"))
					transition(edgeName="t033",targetState="turnOffMsg",cond=whenDispatch("turnOffLed"))
				}	 
				state("turnOnMsg") { //this:State
					action { //it:State
						
									wsLed.LedUtils.printLedState("\tLED | LED ON")
									wsLed.LedUtils.turnOn()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("turnOffMsg") { //this:State
					action { //it:State
						
									wsLed.LedUtils.printLedState("\tLED | LED OFF")
									wsLed.LedUtils.turnOff()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handleEvent") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("updateLed(STATE)"), Term.createTerm("updateLed(STATE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 LedState = ws.LedState.valueOf(payloadArg(0))  
								updateResourceRep( "led($LedState)"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="blinkLedOn", cond=doswitchGuarded({ LedState.equals(ws.LedState.BLINKING)  
					}) )
					transition( edgeName="goto",targetState="notBlink", cond=doswitchGuarded({! ( LedState.equals(ws.LedState.BLINKING)  
					) }) )
				}	 
				state("notBlink") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="on", cond=doswitchGuarded({ LedState.equals(ws.LedState.ON)  
					}) )
					transition( edgeName="goto",targetState="off", cond=doswitchGuarded({! ( LedState.equals(ws.LedState.ON)  
					) }) )
				}	 
				state("on") { //this:State
					action { //it:State
						 
									wsLed.LedUtils.printLedState("\tLED | led ON")
									wsLed.LedUtils.turnOn()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t034",targetState="handleEvent",cond=whenDispatch("updateLed"))
				}	 
				state("off") { //this:State
					action { //it:State
						
									wsLed.LedUtils.printLedState("\tLED | led OFF")
									wsLed.LedUtils.turnOff()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t035",targetState="handleEvent",cond=whenDispatch("updateLed"))
				}	 
				state("blinkLedOn") { //this:State
					action { //it:State
						 
									wsLed.LedUtils.printLedState("\tLED | led Blink")
									wsLed.LedUtils.turnOn()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		//sysaction { //it:State
				 	 		  stateTimer = TimerActor("timer_blinkLedOn", 
				 	 			scope, context!!, "local_tout_led_blinkLedOn", 300.toLong() )
				 	 		//}
					}	 	 
					 transition(edgeName="t036",targetState="blinkLedOff",cond=whenTimeout("local_tout_led_blinkLedOn"))   
					transition(edgeName="t037",targetState="handleEvent",cond=whenDispatch("updateLed"))
				}	 
				state("blinkLedOff") { //this:State
					action { //it:State
						 
									wsLed.LedUtils.printLedState("\tLED | led Blink")
									wsLed.LedUtils.turnOff()
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		//sysaction { //it:State
				 	 		  stateTimer = TimerActor("timer_blinkLedOff", 
				 	 			scope, context!!, "local_tout_led_blinkLedOff", 300.toLong() )
				 	 		//}
					}	 	 
					 transition(edgeName="t038",targetState="blinkLedOn",cond=whenTimeout("local_tout_led_blinkLedOff"))   
					transition(edgeName="t039",targetState="handleEvent",cond=whenDispatch("updateLed"))
				}	 
			}
		}
}
