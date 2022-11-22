import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.net.ConnectException
import java.util.*

fun main() {
    val client = HttpClient(CIO) {
        install(WebSockets) {
            pingInterval = 20_000
        }
    }
    runBlocking {
        while (true) {
            try {
                client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8085, path = "/socket") {
                    while(true) {
                        val myMessage = Scanner(System.`in`).next()
                        if(myMessage != null) {
                            send(myMessage)
                        }
                    }
                }
            } catch (e: ConnectException) {
                println("WAIT/RETRY FOR STATUS-GUI SERVER...")
                delay(1000)
            }
        }

    }
    client.close()
}