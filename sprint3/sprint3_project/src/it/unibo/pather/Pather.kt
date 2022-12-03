/* Generated by AN DISI Unibo */ 
package it.unibo.pather

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Pather ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "init"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 
				var CurMoveTodo = ""
				var Pathing = false 
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						 CurMoveTodo = ""  
						 sysUtil.logMsgs = true  
						 Pathing = false  
						println("	PATHEXECUTOR | started")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t015",targetState="doThePath",cond=whenRequest("doPath"))
					transition(edgeName="t016",targetState="handleHalt",cond=whenDispatch("halt"))
				}	 
				state("doThePath") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("doPath(PATH,OWNER)"), Term.createTerm("doPath(P,C)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val path = payloadArg(0)
												println(path)
												ws.pathing.pathut.setPath(path)
						}
						println("	PATHEXECUTOR | pathTodo: ${ws.pathing.pathut.getPathTodo()}")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="nextMove", cond=doswitch() )
				}	 
				state("nextMove") { //this:State
					action { //it:State
						 CurMoveTodo = ws.pathing.pathut.nextMove()  
						println("	PATHEXECUTOR | curMoveTodo: $CurMoveTodo")
						 Pathing = true  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="endWorkOk", cond=doswitchGuarded({ CurMoveTodo.length == 0  
					}) )
					transition( edgeName="goto",targetState="doMove", cond=doswitchGuarded({! ( CurMoveTodo.length == 0  
					) }) )
				}	 
				state("handleStopPath") { //this:State
					action { //it:State
						answer("stopPath", "stopAck", "stopAck(_)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("doMove") { //this:State
					action { //it:State
						 ws.pathing.planner.updateMap(CurMoveTodo, "")  
						delay(350) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="doMoveW", cond=doswitchGuarded({ CurMoveTodo == "w"  
					}) )
					transition( edgeName="goto",targetState="doMoveTurn", cond=doswitchGuarded({! ( CurMoveTodo == "w"  
					) }) )
				}	 
				state("doMoveTurn") { //this:State
					action { //it:State
						forward("cmd", "cmd($CurMoveTodo)" ,"basicrobot" ) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
				 	 		//sysaction { //it:State
				 	 		  stateTimer = TimerActor("timer_doMoveTurn", 
				 	 			scope, context!!, "local_tout_pather_doMoveTurn", 350.toLong() )
				 	 		//}
					}	 	 
					 transition(edgeName="t117",targetState="nextMove",cond=whenTimeout("local_tout_pather_doMoveTurn"))   
					transition(edgeName="t118",targetState="handleHalt",cond=whenDispatch("halt"))
				}	 
				state("doMoveW") { //this:State
					action { //it:State
						request("step", "step(350)" ,"basicrobot" )  
						delay(350) 
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t219",targetState="handleHalt",cond=whenDispatch("halt"))
					transition(edgeName="t220",targetState="nextMove",cond=whenReply("stepdone"))
					transition(edgeName="t221",targetState="handleAlarm",cond=whenEvent("alarm"))
					transition(edgeName="t222",targetState="endWorkKo",cond=whenReply("stepfail"))
					transition(edgeName="t223",targetState="handleStopPath",cond=whenRequest("stopPath"))
				}	 
				state("handleHalt") { //this:State
					action { //it:State
						forward("updateRobotState", "updateRobotState(HALT)" ,"wsgui" ) 
						println("	PATHEXECUTOR | stop...")
						updateResourceRep( "pather(halt_begin)"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t124",targetState="resumeFromHalt",cond=whenDispatch("resume"))
				}	 
				state("resumeFromHalt") { //this:State
					action { //it:State
						forward("updateRobotState", "updateRobotState(RESUME)" ,"wsgui" ) 
						println("	PATHEXECUTOR | resuming from halt")
						updateResourceRep( "pather(halt_end)"  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="nextMove", cond=doswitchGuarded({ Pathing  
					}) )
					transition( edgeName="goto",targetState="init", cond=doswitchGuarded({! ( Pathing  
					) }) )
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						 var PathTodo = ws.pathing.pathut.getPathTodo()  
						println("	PATHEXECUTOR | handleAlarm - pathTodo: $PathTodo")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
				}	 
				state("endWorkOk") { //this:State
					action { //it:State
						println("	PATHEXECUTOR | path done")
						answer("doPath", "doPathDone", "doPathDone(OK)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("endWorkKo") { //this:State
					action { //it:State
						 var PathStillTodo = ws.pathing.pathut.getPathTodo()  
						println("	PATHEXECUTOR | path failure - PathStillTodo: $PathStillTodo")
						answer("doPath", "doPathFail", "doPathFail($PathStillTodo)"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t325",targetState="handleAlarm",cond=whenEvent("alarm"))
				}	 
			}
		}
}