package dev.davivieira.topologyinventory.framework.adapters.output.mysql.mappers;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Location;
import dev.davivieira.topologyinventory.domain.vo.Model;
import dev.davivieira.topologyinventory.domain.vo.Network;
import dev.davivieira.topologyinventory.domain.vo.RouterType;
import dev.davivieira.topologyinventory.domain.vo.SwitchType;
import dev.davivieira.topologyinventory.domain.vo.Vendor;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.IPData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.LocationData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.ModelData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.NetworkData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.RouterData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.RouterTypeData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.SwitchData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.SwitchTypeData;
import dev.davivieira.topologyinventory.framework.adapters.output.mysql.data.VendorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static dev.davivieira.topologyinventory.domain.vo.Id.withId;

public class RouterH2Mapper {

    public static Router routerDataToDomain(RouterData routerData){
        var router = RouterFactory.getRouter(
                withId(routerData.getRouterId().toString()),
                Vendor.valueOf(routerData.getRouterVendor().toString()),
                Model.valueOf(routerData.getRouterModel().toString()),
                IP.fromAddress(routerData.getIp().getAddress()),
                locationDataToLocation(routerData.getRouterLocation()),
                RouterType.valueOf(routerData.getRouterType().name()));
        if(routerData.getRouterParentCoreId()!=null)
        router.setParentRouterId(Id.withId(routerData.getRouterParentCoreId().toString()));
        if(routerData.getRouterType().equals(RouterTypeData.CORE)){
            var coreRouter = (CoreRouter) router;
            coreRouter.setRouters(getRoutersFromData(routerData.getRouters()));
            return coreRouter;
        } else {
            var edgeRouter = (EdgeRouter) router;
            edgeRouter.setSwitches(getSwitchesFromData(routerData.getSwitches()));
            return edgeRouter;
        }
    }

    public static RouterData routerDomainToData(Router router){
        var routerData = RouterData.builder().
                routerId(router.getId().getUuid()).
                routerVendor(VendorData.valueOf(router.getVendor().toString())).
                routerModel(ModelData.valueOf(router.getModel().toString())).
                ip(IPData.fromAddress(router.getIp().getIpAddress())).
                routerLocation(locationDomainToLocationData(router.getLocation())).
                routerType(RouterTypeData.valueOf(router.getRouterType().toString())).
                build();
        if(router.getParentRouterId()!=null)
            routerData.setRouterParentCoreId(router.getParentRouterId().getUuid());
        if(router.getRouterType().equals(RouterType.CORE)) {
            var coreRouter = (CoreRouter) router;
            routerData.setRouters(getRoutersFromDomain(coreRouter.getRouters()));
        } else {
            var edgeRouter = (EdgeRouter) router;
            routerData.setSwitches(getSwitchesFromDomain(edgeRouter.getSwitches()));
        }
        return routerData;
    }

    public static Switch switchDataToDomain(SwitchData switchData) {
        return Switch.builder().
                switchId(withId(switchData.getSwitchId().toString())).
                routerId(withId(switchData.getRouterId().toString())).
                vendor(Vendor.valueOf(switchData.getSwitchVendor().toString())).
                model(Model.valueOf(switchData.getSwitchModel().toString())).
                ip(IP.fromAddress(switchData.getIp().getAddress())).
                location(locationDataToLocation(switchData.getSwitchLocation())).
                switchType(SwitchType.valueOf(switchData.getSwitchType().toString())).
                switchNetworks(getNetworksFromData(switchData.getNetworks())).
                build();
    }

    public static SwitchData switchDomainToData(Switch aSwitch){
        return  SwitchData.builder().
                switchId(aSwitch.getId().getUuid()).
                routerId(aSwitch.getRouterId().getUuid()).
                switchVendor(VendorData.valueOf(aSwitch.getVendor().toString())).
                switchModel(ModelData.valueOf(aSwitch.getModel().toString())).
                ip(IPData.fromAddress(aSwitch.getIp().getIpAddress())).
                switchLocation(locationDomainToLocationData(aSwitch.getLocation())).
                switchType(SwitchTypeData.valueOf(aSwitch.getSwitchType().toString())).
                networks(getNetworksFromDomain(aSwitch.getSwitchNetworks(), aSwitch.getId().getUuid())).
                build();
    }

    public static Location locationDataToLocation(LocationData locationData){
        return Location.builder()
                .address(locationData.getAddress())
                .city(locationData.getCity())
                .state(locationData.getState())
                .zipCode(locationData.getZipcode())
                .country(locationData.getCountry())
                .latitude(locationData.getLatitude())
                .longitude(locationData.getLongitude())
                .build();
    }

    public static LocationData locationDomainToLocationData(Location location){
        return LocationData.builder()
                .address(location.getAddress())
                .city(location.getCity())
                .state(location.getState())
                .zipcode(location.getZipCode())
                .country(location.getCountry())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    private static Map<Id, Router> getRoutersFromData(Set<RouterData> routerDataList){
        Map<Id,Router> routerMap = new HashMap<>();
        for (RouterData routerData : routerDataList) {
            routerMap.put(
                    withId(routerData.getRouterId().toString()),
                    routerDataToDomain(routerData));
        }
        return routerMap;
    }

    private static Set<RouterData>  getRoutersFromDomain(Map<Id, Router> routers){
        Set<RouterData> routerDataList = new HashSet<>();
         routers.values().stream().forEach(router -> {
             var routerData = routerDomainToData(router);
             routerDataList.add(routerData);
         });
        return routerDataList;
    }

    private static Map<Id, Switch> getSwitchesFromData(List<SwitchData> switchDataList){
        Map<Id,Switch> switchMap = new HashMap<>();
        for (SwitchData switchData : switchDataList) {
            switchMap.put(
                    withId(switchData.getSwitchId().toString()),
                    switchDataToDomain(switchData));
        }
        return switchMap;
    }

    private static List<SwitchData>  getSwitchesFromDomain(Map<Id, Switch> switches){
        List<SwitchData> switchDataList = new ArrayList<>();
        if(switches!=null) {
            switches.values().stream().forEach(aSwitch -> {
                switchDataList.add(switchDomainToData(aSwitch));
            });
        }
        return switchDataList;
    }

    private static List<Network> getNetworksFromData(Set<NetworkData> networkData){
        List<Network> networks = new ArrayList<>();
        networkData.forEach(data ->{
            var network = new Network(
                    IP.fromAddress(data.getIp().getAddress()),
                    data.getName(),
                    data.getCidr());
            networks.add(network);
        });
        return networks;
    }

    private static Set<NetworkData>  getNetworksFromDomain(List<Network> networks, UUID routerId){
        Set<NetworkData> networkDataSet = new HashSet<>();
        if(networks!=null) {
            networks.forEach(network -> {
                var networkData = new NetworkData(

                        IPData.fromAddress(network.getNetworkAddress().getIpAddress()),
                        network.getNetworkName(),
                        network.getNetworkCidr()
                );
                networkDataSet.add(networkData);
            });
        }
        return networkDataSet;
    }

}
