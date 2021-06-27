package dev.davivieira;

import dev.davivieira.framework.adapters.input.stdin.RouterViewCLIAdapter;

public class App {

    public static void main(String... args) {
        var cli = new RouterViewCLIAdapter();
        var type = "CORE";
        System.out.println(cli.obtainRelatedRouters(type));
    }
}