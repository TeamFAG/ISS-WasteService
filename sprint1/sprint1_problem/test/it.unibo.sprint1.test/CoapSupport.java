package it.unibo.sprint1.test;

import it.unibo.ctxwasteservice.MainCtxwasteserviceKt;
import it.unibo.kactor.MsgUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import java.io.IOException;

public class CoapSupport {

    private CoapClient client;
    private CoapObserveRelation relation = null;

    public CoapSupport() {

    }

    public void connectTo(String address, String path, boolean asObserver) {
        String url = "coap://" + address + "/" + path;

        client = new CoapClient(url);
        MsgUtil.outyellow("CoapSupport | Starts url: " + url);
        client.setTimeout(1000L);

        if(asObserver)
            observeTheResource();
    }

    public String readResource() throws Exception {
        CoapResponse response = client.get();

        if(response != null) {
            ResponseCode code = response.getCode();
            MsgUtil.outgreen("CoapSupport | readResource RESPONSE CODE: " + code);

            if(code == ResponseCode.NOT_FOUND) {
                removeObserve();
                observeTheResource();
            }

            return response.getResponseText();
        }
        else return null;
    }

    public void removeObserve() {
        relation.proactiveCancel();
    }

    public void observeResource(CoapHandler handler) {
        relation = client.observe(handler);
    }

    public void observeTheResource() {
        relation = client.observe(new MyCoapHandler());
    }

    public boolean callTheResource(String msg) throws ConnectorException, IOException {
        CoapResponse response = client.put(msg, MediaTypeRegistry.TEXT_PLAIN);

        if(response != null)
            MsgUtil.outgreen("CoapSupport | updateResource RESPONSE CODE: " + response.getCode());
        else
            MsgUtil.outred("CoapSupport | updateResource FAILS");

        return response != null;
    }

    public void readTheResource() {
        try {
            String v = readResource();
            MsgUtil.outgreen("CoapSupport | readTheResource reads: " + v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        CoapSupport cs = new CoapSupport();
        cs.connectTo("127.0.0.1:8038", "ctxwasteservice/trolleymover", true);
        cs.readTheResource();

        new Thread() {
            public void run() {
                MainCtxwasteserviceKt.main();
            }
        }.start();

        ConnTcp conn = new ConnTcp("127.0.0.1", 8038);
        String answer = conn.request(MsgUtil.buildRequest("transporttrolley", "move", "move(indoor)", "trolleymover").toString());

        conn.close();
    }

    public static void main(String[] args) {
        CoapSupport.test();
    }
}
