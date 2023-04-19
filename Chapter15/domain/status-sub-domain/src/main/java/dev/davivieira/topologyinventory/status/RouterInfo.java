package dev.davivieira.topologyinventory.status;

import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.valueobject.IP;
import dev.davivieira.topologyinventory.domain.valueobject.Id;
import dev.davivieira.topologyinventory.domain.valueobject.Model;
import dev.davivieira.topologyinventory.domain.valueobject.RouterType;
import dev.davivieira.topologyinventory.domain.valueobject.Vendor;

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
