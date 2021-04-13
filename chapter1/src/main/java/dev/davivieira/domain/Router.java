package dev.davivieira.domain;

import java.util.ArrayList;
import java.util.List;

public class Router {

    private final Type type;

    private final RouterId id;

    public Router(Type type, RouterId id) {
        this.type = type;
        this.id = id;
    }

    public static List<Router> checkRouter(Type type, List<Router> routers) {
        var routersList = new ArrayList<Router>();
        routers.forEach(router -> {
            if(router.type == type ){
                routersList.add(router);
            }
        });
        return routersList;
    }

    @Override
    public String toString() {
        return "Router{" +
                "type=" + type +
                ", id=" + id +
                '}';
    }
}