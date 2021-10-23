package dev.davivieira.topologyinventory.subdomain;

import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;

public class RouterInfo {

    public String getRouterStatus () {
        var router = RouterFactory.getRouter(
                Id.withoutId(),
                Vendor.CISCO,
                Model.XYZ0004,
                IP.fromAddress("55.0.0.1"),
                null,
                RouterType.CORE);
        return "Router with "+router.getIp()+" is alive!";
    }
}
