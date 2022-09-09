package it.unibo.tests;

import it.unibo.ctxdeposit.MainCtxdepositKt;
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

import static org.junit.Assert.assertNotNull;

public class TestDeposit {

    public final static String HOST = "localhost";
    public final static int PORT = 8020;

    @Before
    public void up() {
        CommSystemConfig.tracing = false;

        new Thread(MainCtxdepositKt::main).start();

        ColorsOut.outappl("transporttrolley waiting", ColorsOut.BLUE);

        ActorBasic actorBasic = QakContext.Companion.getActor("transporttrollery");
        while(actorBasic == null) {
            CommUtils.delay(500);
            actorBasic = QakContext.Companion.getActor("transporttrolley");
        }

        ColorsOut.outappl("transporttrolley ok", ColorsOut.BLUE);
    }

    @After
    public void down() {
        ColorsOut.outappl("Test done", ColorsOut.BLUE);
    }

    @Test
    public void testDeposit() {
        String updateDispatch = null;

        ConnTcp conn = simulateDepositDispatch(Material.PLASTIC, 10.0);

        int maxSeconds = 5;
        int i = 0;
        try {
            updateDispatch = conn.receiveMsg();
            while (updateDispatch == null && i < maxSeconds) {
                updateDispatch = conn.receiveMsg();
                i++;
                CommUtils.delay(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(updateDispatch);
    }

    private ConnTcp simulateDepositDispatch(Material material, Double quantity) {
        String dispatch = MsgUtil.buildDispatch("test", "notifyDeposit", "notifyDeposit(" + material + ", " + quantity + ")", "transporttrolley").toString();
        ConnTcp conn = null;

        try {
            conn = new ConnTcp(HOST, PORT);
            conn.forward(dispatch);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}
