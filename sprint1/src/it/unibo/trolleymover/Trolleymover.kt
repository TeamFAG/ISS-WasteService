/* Generated by AN DISI Unibo */ 
package it.unibo.trolleymover

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import Coordinate

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
				planner.initAI()
				planner.loadRoomMap("mapWithObst2019")
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
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
					 transition(edgeName="t03",targetState="handleMovement",cond=whenRequest("move"))
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
						request("stopPath", "stopPath(_)" ,"pathexecutor" )  
						}
						else
						 {
						 				var coord: Coordinate = utils.getClosestCoordinate(planner.get_curCoord(), LOC)
						 				planner.setGoal(coord.x, coord.y)
						 				planner.doPlan()
						 				Actions = planner.getActionsString()
						 				IsMoving = true
						 println("	TROLLEYMOVER | actions: $Actions")
						 request("doPath", "doPath($Actions,trolleymover)" ,"pathexecutor" )  
						 }
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t14",targetState="handlePathDone",cond=whenReply("doPathDone"))
					transition(edgeName="t15",targetState="handlePathFail",cond=whenReply("doPathFail"))
					transition(edgeName="t16",targetState="handleInterruptedMovement",cond=whenReply("progessReply"))
					transition(edgeName="t17",targetState="handleMovement",cond=whenRequest("move"))
				}	 
				state("handleInterruptedMovement") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("progressReply(PROGRESS)"), Term.createTerm("progressReply(PROGRESS)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												IsMoving = false
												Progress = payloadArg(0)
												planner.updateRobotPosition(Progress)
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
