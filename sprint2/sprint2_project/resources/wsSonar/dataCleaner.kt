package wsSonar

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.IApplMessage


class dataCleaner (name : String ) : ActorBasic( name ) {
	val LimitLow  = SystemConfig.sonar["min"] as Int
	val LimitHigh = SystemConfig.sonar["max"] as Int

    override suspend fun actorBody(msg: IApplMessage) {
		if(msg.msgId() != "sonar")
			return
  		elabData( msg )
 	}

	suspend fun elabData( msg: IApplMessage ) {
		println("$tt $name | CLEANER $msg")

 		val data  = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0).toString()
		val Distance = Integer.parseInt( data )
 		if( Distance > LimitLow && Distance < LimitHigh ){
			emitLocalStreamEvent( msg ) //propagate
     	}else{
			println("$tt $name |  DISCARDS $Distance ")
 		}				
 	}
}