package dev.davivieira.framework.adapters.output.h2.mappers;

import dev.davivieira.domain.entity.Router;
import dev.davivieira.domain.entity.Switch;
import dev.davivieira.domain.vo.*;
import dev.davivieira.framework.adapters.output.h2.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    public class RouterH2Mapper {

        public static Router toDomain(RouterData routerData){
            var routerType = RouterType.valueOf(routerData.getRouterType().name());
            var routerId = RouterId.withId(routerData.getRouterId().toString());
            var switchId = SwitchId.withId(routerData.getNetworkSwitch().getSwitchId().toString());
            var switchType = SwitchType.valueOf(routerData.getNetworkSwitch().getSwitchType().toString());
            var ip = IP.fromAddress(routerData.getNetworkSwitch().getIp().getAddress());
            var networks =  getNetworksFromData(routerData.getNetworkSwitch().getNetworks());

            var networkSwitch = new Switch(switchId, switchType,networks, ip);
            return new Router(routerType, routerId, networkSwitch);
        }


        public static RouterData toH2(Router router){
            var routerTypeData = RouterTypeData.valueOf(router.getType().toString());
            var routerId = router.getId().getUUID();
            var switchId = router.getNetworkSwitch().getSwitchId().getUUID();
            var switchTypeData = SwitchTypeData.valueOf(router.getNetworkSwitch().getSwitchType().toString());
            var ipData = IPData.fromAddress(router.getNetworkSwitch().getAddress().getIPAddress());
            var networkDataList = getNetworksFromDomain(router.retrieveNetworks(), routerId);

            var switchData = new SwitchData(
                    routerId,
                    switchId,
                    switchTypeData,
                    networkDataList,
                    ipData);
            return new RouterData(routerId, routerTypeData, switchData);
        }



        private static List<Network> getNetworksFromData(List<NetworkData> networkData){
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

        private static List<NetworkData> getNetworksFromDomain(List<Network> networks, UUID switchId){
            List<NetworkData> networkDataList = new ArrayList<>();
            networks.forEach(network ->{
                var networkData = new NetworkData(
                        switchId,
                        IPData.fromAddress(network.getAddress().getIPAddress()),
                        network.getName(),
                        network.getCidr()
                );
                networkDataList.add(networkData);
            });
            return networkDataList;
        }
    }
