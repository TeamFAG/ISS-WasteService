/* Generated by AN DISI Unibo */ 
package it.unibo.ctxwasteservice_trolley
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "transporttrolley_system.pl", "sysRules.pl","ctxwasteservice_trolley"
	)
}

