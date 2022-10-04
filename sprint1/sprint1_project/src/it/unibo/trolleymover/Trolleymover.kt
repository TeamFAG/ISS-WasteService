/* Generated by AN DISI Unibo */ 
package it.unibo.trolleymover

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Trolleymover ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var Actions: String = "" 
				var LOC: String = ""
				var IsMoving = false
				var Progress = ""
				SystemConfig.setTheConfiguration("SystemConfiguration")
				planner.initAI()
				planner.loadRoomMap("mapRoomEmpty")
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						discardMessages = true
						println("	TROLLEYMOVER | started.")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("idle") { //this:State
					action { //it:State
						println("	TROLLEYMOVER | waiting...")
						 IsMoving = false  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t012",targetState="handleMovement",cond=whenRequest("move"))
				}	 
				state("handleMovement") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("move(LOCATION)"), Term.createTerm("move(LOCATION)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 LOC = payloadArg(0)  
						}
						println("	TROLLEYMOVER | received movement to $LOC")
						if(  IsMoving  
						 ){println("	TROLLEYMOVER | arrived move command when moving.")
						updateResourceRep( "trolleymover(handleMovement_stopPath)"  
						)
						request("stopPath", "stopPath(_)" ,"ourpathexecutor" )  
						}
						else
						 {
						 				var coord = utils.getClosestCoordinate(planner.get_curCoord(), LOC)
						 				planner.setGoal(coord.x, coord.y)
						 				planner.doPlan()
						 				Actions = planner.getActionsString()
						 				IsMoving = true
						 println("	TROLLEYMOVER | actions: $Actions")
						 updateResourceRep( "trolleymover(handleMovement_$LOC)"  
						 )
						 request("doPath", "doPath($Actions,trolleymover)" ,"ourpathexecutor" )  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t113",targetState="handlePathDone",cond=whenReply("doPathDone"))
					transition(edgeName="t114",targetState="handlePathFail",cond=whenReply("doPathFail"))
					transition(edgeName="t115",targetState="handleInterruptedMovement",cond=whenReply("stopACK"))
					transition(edgeName="t116",targetState="handleMovement",cond=whenRequest("move"))
				}	 
				state("handleInterruptedMovement") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("stopACK(_)"), Term.createTerm("stopACK(_)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 IsMoving = false  
								updateResourceRep( "trolleymover(handleInterruptedMovement)"  
								)
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="handleMovement", cond=doswitch() )
				}	 
				state("handlePathDone") { //this:State
					action { //it:State
						 IsMoving = false  
						println("	TROLLEYMOVER | arrived to $LOC")
						updateResourceRep( "trolleymover(handlePathDone_$LOC)"  
						)
						answer("move", "moveDone", "moveDone(OK)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="idle", cond=doswitch() )
				}	 
				state("handlePathFail") { //this:State
					action { //it:State
						 IsMoving = false  
						println("	TROLLEYMOVER | path failed to $LOC")
						updateResourceRep( "trolleymover(handlePathFail_$LOC)"  
						)
						answer("move", "moveDone", "moveDone(NO)"   )  
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
