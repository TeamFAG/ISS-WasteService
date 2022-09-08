package it.unibo.tests;

import it.unibo.ctxrequest.MainCtxrequestKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import it.unibo.kactor.QakContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unibo.comm22.utils.ColorsOut;
import unibo.comm22.utils.CommSystemConfig;
import unibo.comm22.utils.CommUtils;
import ws.Material;

import static org.junit.Assert.assertTrue;

public class TestRequest {

    public final static String HOST = "localhost";
    public final static int PORT = 8010;

    @Before
    public void up() {
        CommSystemConfig.tracing = false;

        new Thread(MainCtxrequestKt::main).start();

        ColorsOut.outappl("wasteservice waiting", ColorsOut.BLUE);

        ActorBasic actorBasic = QakContext.Companion.getActor("wasteservice");
        while(actorBasic == null) {
            CommUtils.delay(500);
            actorBasic = QakContext.Companion.getActor("wasteservice");
        }

        ColorsOut.outappl("wasteservice ok", ColorsOut.BLUE);
    }

    @After
    public void down() {
        ColorsOut.outappl("Tests done", ColorsOut.BLUE);
    }

    @Test
    public void testLoadAcceptedPlastic() {
        String reply = simulateRequest(Material.PLASTIC, 10.0);
        ColorsOut.outappl("Reply: " + reply, ColorsOut.GREEN);
        assertTrue(reply.contains("loadAccepted"));
    }

    @Test
    public void testLoadAcceptedGlass() {
        String reply = simulateRequest(Material.GLASS, 10.0);
        ColorsOut.outappl("Reply: " + reply, ColorsOut.GREEN);
        assertTrue(reply.contains("loadAccepted"));
    }

    @Test
    public void testLoadRejectedGlass() {
        String reply = simulateRequest(Material.GLASS, 150.0);
        ColorsOut.outappl("Reply: " + reply, ColorsOut.GREEN);
        assertTrue(reply.contains("loadRejected"));
    }

    @Test
    public void testLoadRejectedPlastic() {
        String reply = simulateRequest(Material.PLASTIC, 150.0);
        ColorsOut.outappl("Reply: " + reply, ColorsOut.GREEN);
        assertTrue(reply.contains("loadRejected"));
    }

    private String simulateRequest(Material material, Double quantity) {
        String request = MsgUtil.buildRequest("test", "storeRequest", "storeRequest(" + material + ", " + quantity + ")", "wasteservice").toString();

        try {
            ConnTcp conn = new ConnTcp(HOST, PORT);
            return conn.request(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
