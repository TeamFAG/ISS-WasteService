/* Generated by AN DISI Unibo */ 
package it.unibo.ctxgui
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "sprint3_problem.pl", "sysRules.pl","ctxgui"
	)
}
