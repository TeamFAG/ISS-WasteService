/* Generated by AN DISI Unibo */ 
package it.unibo.ctxwasteservice
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "sprint3_project.pl", "sysRules.pl","ctxwasteservice"
	)
}

