package it.unibo.WasteserviceStatusManager

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WebSocketController {

    @RequestMapping("/home")
    fun textOnly(): String {
        return "index"
    }
}