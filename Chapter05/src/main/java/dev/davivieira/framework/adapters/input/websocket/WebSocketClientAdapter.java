package dev.davivieira.framework.adapters.input.websocket;

import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

public class WebSocketClientAdapter extends org.java_websocket.client.WebSocketClient {

    public WebSocketClientAdapter(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onMessage(String message) {
        String channel = message;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("opened connection");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed connection");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}