package wsSonar
import it.unibo.kactor.*
import alice.tuprolog.Term
import alice.tuprolog.Struct
import unibo.comm22.utils.ColorsOut

class distanceFilter (name : String ) : ActorBasic( name ) {
	val LimitDistance = SystemConfig.sonar["dlimit"] as Int
	var underThreshold = false

    override suspend fun actorBody(msg: IApplMessage) {
		if( msg.msgId() != "sonar")
			return
  		elabData( msg )
 	}

	suspend fun elabData( msg: IApplMessage ){ //msg( "sonarRobor, event, EMITTER, none, sonar(D), N")
 		val data  = (Term.createTerm( msg.msgContent() ) as Struct).getArg(0).toString()

		val Distance = Integer.parseInt( data )
 		if( Distance < LimitDistance && !underThreshold){
	 		val m1 = MsgUtil.buildEvent(name, "startHalt", "startHalt(_)")
			underThreshold = true
			ColorsOut.outappl("$tt $name | DISTANCE: $Distance", ColorsOut.GREEN)
			ColorsOut.outappl("$tt $name | DISTANCE IS UNDER THRESHOLD", ColorsOut.GREEN)
			//emit( m1 ) //propagate event obstacle
			//forward("startHalt", "startHalt(_)", "halteventshandler")
			emit("startHalt", "startHalt(_)")
     	}else if( Distance > LimitDistance && underThreshold) {
			val m1 = MsgUtil.buildEvent(name, "stopHalt", "stopHalt(_)")
			underThreshold = false
			ColorsOut.outappl("$tt $name | DISTANCE: $Distance", ColorsOut.GREEN)
			ColorsOut.outappl("$tt $name | DISTANCE IS NO MORE UNDER THRESHOLD", ColorsOut.GREEN)
			//emit( m1 )
			//forward("stopHalt", "stopHalt(_)", "halteventshandler")
			emit("stopHalt", "stopHalt(_)")
		}
	}
}