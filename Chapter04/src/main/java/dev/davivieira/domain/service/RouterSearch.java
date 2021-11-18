package dev.davivieira.domain.service;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.vo.RouterType;

import java.util.ArrayList;
import java.util.List;

public class RouterSearch {

    public static List<Router> getRouters(RouterType type, List<Router> routers) {
        var routersList = new ArrayList<Router>();
        routers.forEach(router -> {
            if(router.isType(type)){
                routersList.add(router);
            }
        });
        return routersList;
    }
}
