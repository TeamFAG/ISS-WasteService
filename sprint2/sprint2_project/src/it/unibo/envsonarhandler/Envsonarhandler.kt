/* Generated by AN DISI Unibo */ 
package it.unibo.envsonarhandler

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Envsonarhandler ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("envsonarhandler | (RE)START")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t148",targetState="dontcare",cond=whenDispatch("cmd"))
					transition(edgeName="t149",targetState="handleEnvSonar",cond=whenEvent("sonar"))
				}	 
				state("dontcare") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("handleEnvSonar") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t150",targetState="handleEnvSonar",cond=whenEvent("sonar"))
				}	 
			}
		}
}
