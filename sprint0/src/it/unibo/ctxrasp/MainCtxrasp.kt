/* Generated by AN DISI Unibo */ 
package it.unibo.ctxrasp
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "systemarchitecture.pl", "sysRules.pl","ctxrasp"
	)
}

