package wsSonar

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.IApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import unibo.comm22.utils.ColorsOut
import java.io.BufferedReader
import java.io.InputStreamReader

class sonarEmitterConcrete(name: String): ActorBasic(name) {

    lateinit var reader: BufferedReader

    init{
        println("\tsonarEmitterConcrete | Started.")
    }

    override suspend fun actorBody(msg: IApplMessage) {
        try{
            val p = Runtime.getRuntime().exec("sudo ./SonarAlone")
            reader = BufferedReader(InputStreamReader(p.getInputStream()))
            startRead()
        }catch( e : Exception){
            e.printStackTrace()
            println("$tt $name | WARNING:  does not find SonarAlone")
        }
    }

    suspend fun startRead(){
        var counter = 0
        GlobalScope.launch{	//to allow message handling
            while( true ){
                var data = reader.readLine()
                //println("sonarHCSR04Support data = $data"   )
                if( data != null ){
                    try{
                        val v = data.toInt()
                        if( v <= 150 ){	//A first filter ...
                            val m1 = "distance($v)"
                            val event = MsgUtil.buildEvent( name,"sonar",m1)

                            emitLocalStreamEvent( event )		//not propagated to remote actors
                            //println("sonarEmitterConcrete | ${counter++}: $event "   )
                        }
                    }catch(e: Exception){}
                }
            }
        }
    }
}