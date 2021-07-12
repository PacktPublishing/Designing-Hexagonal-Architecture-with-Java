package dev.davivieira;

import com.sun.net.httpserver.HttpServer;
import dev.davivieira.framework.adapters.input.RouterNetworkAdapter;
import dev.davivieira.framework.adapters.input.rest.RouterNetworkRestAdapter;
import dev.davivieira.framework.adapters.input.stdin.RouterNetworkCLIAdapter;
import dev.davivieira.framework.adapters.input.websocket.NotifyEventWebSocketAdapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class App {

    private RouterNetworkAdapter routerNetworkAdapter;

    public static void main(String... args) throws IOException, InterruptedException {
        var adapter = "cli";
        if(args.length>0) {
            adapter = args[0];
        }
        new App().setAdapter(adapter);
    }

    void setAdapter(String adapter) throws IOException, InterruptedException {
        switch (adapter){
            case "rest":
                this.routerNetworkAdapter = new RouterNetworkRestAdapter();
                rest();
                NotifyEventWebSocketAdapter.startServer();
                break;
            default:
                this.routerNetworkAdapter = new RouterNetworkCLIAdapter();
                cli();
        }
    }

    private void cli() {
        Scanner scanner = new Scanner(System.in);
        routerNetworkAdapter.processRequest(scanner);
    }

    private void rest() {
        try {
            System.out.println("REST endpoint listening on port 8080...");
            var httpserver = HttpServer.create(new InetSocketAddress(8080), 0);
            routerNetworkAdapter.processRequest(httpserver);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}