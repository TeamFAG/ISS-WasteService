/* Generated by AN DISI Unibo */ 
package it.unibo.wsgui

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Wsgui ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var LedState = ws.LedState.OFF
				var GlassQty: Float = 0F
				var PlasticQty: Float = 0F
				var RobotState: String = "HOME - Idle"
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("	WSGUI | started.")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t037",targetState="updLedHandler",cond=whenDispatch("updateLed"))
					transition(edgeName="t038",targetState="updMaterialHandler",cond=whenDispatch("updateMaterial"))
					transition(edgeName="t039",targetState="updRobotHandler",cond=whenDispatch("updateRobotState"))
				}	 
				state("updLedHandler") { //this:State
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
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("updMaterialHandler") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("updateMaterial(MATERIAL,QUANTITY)"), Term.createTerm("updateMaterial(MATERIAL,QUANTITY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												CurrentMaterial = ws.Material.valueOf(payloadArg(0))
												CurrentQuantity = payloadArg(1).toFloat()
								if(  CurrentMaterial.equals(ws.Material.GLASS)  
								 ){
													GlassQty = CurrentQuantity
								updateResourceRep( "WSGUI | GLASS UPDATE - $GlassQty"  
								)
								println("	WSGUI | GLASS UPDATE - $GlassQty")
								}
								else
								 {
								 					PlasticQty = CurrentQuantity
								 updateResourceRep( "WSGUI | PLASTIC UPDATE - $PlastixQty"  
								 )
								 println("	WSGUI | PLASTIC UPDATE - $PlastixQty")
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("updRobotHandler") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("updateRobotState(STATE)"), Term.createTerm("updateRobotState(STATE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												RobotState = payloadArg(0).toString()
								updateResourceRep( "WSGUI | ROBOT UPDATE - $RobotState"  
								)
								println("	WSGUI | ROBOT UPDATE - $RobotState")
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