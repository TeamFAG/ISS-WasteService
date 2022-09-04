package it.unibo.tests;

import it.unibo.ctxdeposit.MainCtxdepositKt;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.junit.After;
import org.junit.Before;
import unibo.comm22.utils.ColorsOut;
import unibo.comm22.utils.CommSystemConfig;
import unibo.comm22.utils.CommUtils;

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


}
