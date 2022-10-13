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
		return { //this:ActionBasciFsm
				state("init") { //this:State
					action { //it:State
						 CurMoveTodo = ""  
						 sysUtil.logMsgs = true  
						println("	PATHEXECUTOR | started")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t015",targetState="doThePath",cond=whenRequest("doPath"))
				}	 
				state("doThePath") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("doPath(PATH,OWNER)"), Term.createTerm("doPath(P,C)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								
												val path = payloadArg(0)
												println(path)
												pathut.setPath(path)
						}
						println("	PATHEXECUTOR | pathTodo: ${pathut.getPathTodo()}")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="nextMove", cond=doswitch() )
				}	 
				state("nextMove") { //this:State
					action { //it:State
						 CurMoveTodo = pathut.nextMove()  
						println("	PATHEXECUTOR | curMoveTodo: $CurMoveTodo")
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
						answer("stopPath", "stopAck", "stopAck(_)","trolleymover"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("doMove") { //this:State
					action { //it:State
						 planner.updateMap(CurMoveTodo, "")  
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
					 transition(edgeName="t116",targetState="nextMove",cond=whenTimeout("local_tout_pather_doMoveTurn"))   
				}	 
				state("doMoveW") { //this:State
					action { //it:State
						request("step", "step(350)" ,"basicrobot" )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t217",targetState="handleAlarm",cond=whenEvent("alarm"))
					transition(edgeName="t218",targetState="nextMove",cond=whenReply("stepdone"))
					transition(edgeName="t219",targetState="endWorkKo",cond=whenReply("stepfail"))
					transition(edgeName="t220",targetState="handleStopPath",cond=whenRequest("stopPath"))
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						 var PathTodo = pathut.getPathTodo()  
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
						answer("doPath", "doPathDone", "doPathDone(OK)","trolleymover"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="init", cond=doswitch() )
				}	 
				state("endWorkKo") { //this:State
					action { //it:State
						 var PathStillTodo = pathut.getPathTodo()  
						println("	PATHEXECUTOR | path failure - PathStillTodo: $PathStillTodo")
						answer("doPath", "doPathFail", "doPathFail($PathStillTodo)","trolleymover"   )  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t321",targetState="handleAlarm",cond=whenEvent("alarm"))
				}	 
			}
		}
}