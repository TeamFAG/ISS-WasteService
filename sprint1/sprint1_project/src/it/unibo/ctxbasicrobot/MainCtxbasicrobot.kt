/* Generated by AN DISI Unibo */ 
package it.unibo.ctxbasicrobot
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "trolleymover_system.pl", "sysRules.pl","ctxbasicrobot"
	)
}
