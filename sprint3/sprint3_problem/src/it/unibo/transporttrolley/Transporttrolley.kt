/* Generated by AN DISI Unibo */ 
package it.unibo.transporttrolley

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Transporttrolley ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		
				lateinit var Mat: ws.Material
				var Qty: Float = 0F
				var Error: Boolean = false
				var Loc: String = ""
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						println("	TRANSPORTTROLLEY | started.")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						println("	TRANSPORTTROLLEY | waiting for requests...")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t03",targetState="handleDepositRequest",cond=whenRequest("depositRequest"))
				}	 
				state("handleDepositRequest") { //this:State
					action { //it:State
						updateResourceRep( "transporttrolley(handleDepositRequest)"  
						)
						if( checkMsgContent( Term.createTerm("depositRequest(MATERIAL,QUANTITY)"), Term.createTerm("depositRequest(MATERIAL,QUANTITY)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
													Mat = ws.Material.valueOf(payloadArg(0))
													Qty = payloadArg(1).toFloat()
						}
						forward("updateRobotState", "updateRobotState("moving to INDOOR")" ,"wsgui" ) 
						updateResourceRep( "transporttrolley(moving_INDOOR)"  
						)
						println("	TRANSPORTTROLLEY | moving to INDOOR")
						request("move", "move(INDOOR)" ,"trolleymover" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t14",targetState="arrivedIndoor",cond=whenReply("moveDone"))
				}	 
				state("arrivedIndoor") { //this:State
					action { //it:State
						 var Answer: String = ""  
						if( checkMsgContent( Term.createTerm("moveDone(ANSWER)"), Term.createTerm("moveDone(ANSWER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Answer = payloadArg(0).toString()  
						}
						if(  Answer == "OK"  
						 ){forward("updateRobotState", "updateRobotState("INDOOR - Pickup")" ,"wsgui" ) 
						updateResourceRep( "transporttrolley(arrived_INDOOR)"  
						)
						println("	TRANSPORTTROLLEY | arrived to INDOOR")
						delay(2000) 
						updateResourceRep( "transporttrolley(pickupDone)"  
						)
						println("	TRANSPORTTROLLEY | pickup done")
						answer("depositRequest", "pickupDone", "pickupDone(OK)"   )  
						}
						else
						 {forward("updateRobotState", "updateRobotState("INDOOR - Pickup FAIL")" ,"wsgui" ) 
						 println("	TRANSPORTTROLLEY | failed pickup")
						  Error = true  
						 answer("depositRequest", "pickupDone", "pickupDone(NO)"   )  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="moveToBox", cond=doswitchGuarded({ Error == false  
					}) )
					transition( edgeName="goto",targetState="errorHandler", cond=doswitchGuarded({! ( Error == false  
					) }) )
				}	 
				state("moveToBox") { //this:State
					action { //it:State
						 Loc = ws.utils.getLocationFromMaterialType(Mat)  
						forward("updateRobotState", "updateRobotState("moving to $Loc")" ,"wsgui" ) 
						updateResourceRep( "transporttrolley(moving_$Loc)"  
						)
						println("	TRANSPORTTROLLEY | moving to $Loc")
						request("move", "move($Loc)" ,"trolleymover" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t25",targetState="arrivedToBox",cond=whenReply("moveDone"))
				}	 
				state("arrivedToBox") { //this:State
					action { //it:State
						 var Answer = ""  
						if( checkMsgContent( Term.createTerm("moveDone(ANSWER)"), Term.createTerm("moveDone(ANSWER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 Answer = payloadArg(0).toString()  
						}
						if(  Answer == "OK"  
						 ){forward("updateRobotState", "updateRobotState("$Loc")" ,"wsgui" ) 
						updateResourceRep( "transporttrolley(arrived_$Loc)"  
						)
						println("	TRANSPORTTROLLEY | arrived to $Loc")
						}
						else
						 {forward("updateRobotState", "updateRobotState("$Loc - FAILED")" ,"wsgui" ) 
						 println("	TRANSPORTTROLLEY | failed $Loc")
						  Error = true  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="doDeposit", cond=doswitch() )
				}	 
				state("doDeposit") { //this:State
					action { //it:State
						forward("updateRobotState", "updateRobotState("Deposit")" ,"wsgui" ) 
						println("	TRANSPORTTROLLEY | doing the deposit of $Qty KG of $Mat")
						delay(1000) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="depositDone", cond=doswitch() )
				}	 
				state("depositDone") { //this:State
					action { //it:State
						 Loc = "HOME"  
						forward("updateRobotState", "updateRobotState("moving to $Loc")" ,"wsgui" ) 
						updateResourceRep( "transporttrolley(depositDone_$Mat)"  
						)
						println("	TRANSPORTTROLLEY | deposit done of $Qty KG of $Mat")
						request("move", "move($Loc)" ,"trolleymover" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t36",targetState="handleDepositRequest",cond=whenRequest("depositRequest"))
					transition(edgeName="t37",targetState="endWork",cond=whenReply("moveDone"))
				}	 
				state("endWork") { //this:State
					action { //it:State
						forward("updateRobotState", "updateRobotState("HOME - Idle")" ,"wsgui" ) 
						updateResourceRep( "transporttrolley(arrived_HOME)"  
						)
						println("	TRANSPORTTROLLEY | arrived to HOME")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t48",targetState="handleDepositRequest",cond=whenRequest("depositRequest"))
				}	 
				state("errorHandler") { //this:State
					action { //it:State
						updateResourceRep( "transporttrolley(ERROR)"  
						)
						println("	TRANSPORTTROLLEY | error - resetting...")
						
									Qty = 0F
									Error = false
									Loc = ""
						request("move", "move(HOME)" ,"trolleymover" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t59",targetState="idle",cond=whenReply("moveDone"))
				}	 
			}
		}
}
