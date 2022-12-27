package it.unibo.wsGuiManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {
    @RequestMapping("/home")
    public String textOnly() {
        return "index";
    }
}
