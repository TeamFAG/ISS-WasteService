package it.unibo.sprint1.test;

import it.unibo.kactor.MsgUtil;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;

public class MyCoapHandler implements CoapHandler {
    @Override
    public void onLoad(CoapResponse response) {
        String content = response.getResponseText();
        MsgUtil.outblue("Content: " + content);
    }

    @Override
    public void onError() {
        MsgUtil.outred("Handler error");
    }
}
